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
package gplx.xowa.addons.wikis.pages.randoms.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.randoms.*;
import gplx.dbs.*; import gplx.xowa.addons.wikis.pages.randoms.dbs.*;
public class Rndm_bldr_wkr {
	private final    Rndm_qry_tbl qry_tbl; private final    Rndm_rng_tbl rng_tbl; private final    Rndm_seq_tbl seq_tbl;
	private Rndm_qry_itm qry_itm;
	private Db_stmt rng_stmt, seq_stmt;
	private int rng_seq_bgn, seq_in_rng;
	private int qry_idx_max = 0;
	public Rndm_bldr_wkr(Db_conn conn, Rndm_qry_tbl qry_tbl, Rndm_rng_tbl rng_tbl, Rndm_seq_tbl seq_tbl) {
		this.conn = conn;
		this.qry_tbl = qry_tbl; this.rng_tbl = rng_tbl; this.seq_tbl = seq_tbl;
		qry_idx_max = qry_tbl.Select_qry_max();
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public int Qry_idx() {return qry_idx;} private int qry_idx; 
	public int Rng_idx() {return rng_idx;} private int rng_idx;
	public int Seq_in_qry() {return seq_in_qry;} private int seq_in_qry;
	public void Exec_qry_bgn(Rndm_qry_itm qry_itm) {
		this.qry_itm = qry_itm;

		qry_idx = qry_tbl.Select_by_key(qry_itm.Qry_key());
		if (qry_idx == -1)
			qry_idx = ++qry_idx_max; 
		else {
			// delete all
			qry_tbl.Delete_by_qry_idx(qry_idx);
			rng_tbl.Delete_by_qry_idx(qry_idx);
			seq_tbl.Delete_by_qry_idx(qry_idx);
		}

		rng_idx = seq_in_rng = seq_in_qry = 0; 
		rng_stmt = rng_tbl.Insert_stmt();
		seq_stmt = seq_tbl.Insert_stmt();
	}
	public void Exec_qry_end() {
		if (seq_in_qry == 0) return;	// no sequences added
		qry_tbl.Insert(qry_idx, rng_idx, qry_itm.Qry_key(), qry_itm.Qry_data(), qry_itm.Qry_name());
	}
	public void Exec_rng_bgn() {
		rng_seq_bgn = seq_in_qry;
		++rng_idx;
		seq_in_rng = 0;
	}
	public Rndm_rng_itm Exec_rng_end_or_null() {
		if (seq_in_rng == 0) return null;	// no sequences added; return null;
		Rndm_rng_itm rv = new Rndm_rng_itm(qry_idx, rng_idx, rng_seq_bgn, seq_in_qry);
		rng_tbl.Insert(rng_stmt, qry_idx, rng_idx, rng_seq_bgn, seq_in_qry);
		return rv;
	}
	public void Exec_seq_itm(int page_id) {
		seq_tbl.Insert(seq_stmt, qry_idx, rng_idx, seq_in_rng, page_id);
		++seq_in_qry;
		++seq_in_rng;
	}
}
