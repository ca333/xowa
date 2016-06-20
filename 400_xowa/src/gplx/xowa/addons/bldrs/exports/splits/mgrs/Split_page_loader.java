/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.bldrs.exports.splits.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*; import gplx.dbs.metas.*;
import gplx.xowa.wikis.data.tbls.*;
public class Split_page_loader {
	private final    int rows_to_read;
	private final    Xowd_page_tbl tbl; private final    Db_stmt stmt;
	private int score_max = Int_.Max_value;
	public Split_page_loader(Xow_wiki wiki, int rows_to_read) {
		this.rows_to_read = rows_to_read;
		this.tbl = wiki.Data__core_mgr().Tbl__page();
		tbl.Conn().Meta_idx_assert("page", "score__len", Dbmeta_idx_fld.Dsc("page_score"), Dbmeta_idx_fld.Dsc("page_len"));
		this.stmt = tbl.Conn().Stmt_sql("SELECT * FROM page WHERE page_namespace=? AND page_score < ? ORDER BY page_score DESC, page_len DESC");	// ANSI.Y
	}
	public void Init_ns(int ns_id) {score_max = Int_.Max_value;}
	public boolean Load_pages(Split_ctx ctx, List_adp list, Split_wkr[] wkrs, int ns_id) {
		boolean reading = true;
		list.Clear();
		Gfo_log_.Instance.Prog("loading pages", "page_ns", ns_id, "score_max", score_max);
		int score_end = score_max, score_prv = score_max;
		Db_rdr rdr = stmt.Clear().Crt_int("page_namespace", ns_id).Crt_int("page_score", score_max).Exec_select__rls_manual();
		try {
			int rows_count = 0;
			boolean enough_rows_read = false;
			while (true) {
				reading = rdr.Move_next(); if (!reading) break;

				// read data from rdr
				Xowd_page_itm itm = new Xowd_page_itm();
				tbl.Read_page__all(itm, rdr);

				// check if (a) enough rows read and (b) score range is done; (b) needed b/c WHERE is page_score < prv_score
				int score_cur = itm.Score();
				if (enough_rows_read && score_prv != score_cur) {
					score_max = score_prv;	// update score_max for next call; note that (a) score_prv is used to ensure boundary between scores; (b) cur_record will be reread on next call
					break;					// stop reading
				}

				// add to list; update variables
				list.Add(itm);
				if (++rows_count == rows_to_read) enough_rows_read = true;
				score_prv = score_cur;
			}
		} finally {rdr.Rls();}

		// calls wkrs to cache rdrs
		ctx.Page_mgr().Clear();
		for (Split_wkr wkr : wkrs)
			wkr.Split__pages_loaded(ctx, ns_id, score_prv, score_end);
		return reading;
	}
	public void Rls() {
		stmt.Rls();
		tbl.Conn().Meta_idx_delete("page", "score_len");
	}
}
