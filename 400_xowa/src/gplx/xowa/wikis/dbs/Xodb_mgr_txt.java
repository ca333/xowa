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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.ctgs.*; import gplx.xowa.htmls.core.*; import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.pages.*;
public class Xodb_mgr_txt implements Xodb_mgr {
	public Xodb_mgr_txt(Xowe_wiki wiki, Xow_page_mgr data_mgr) {
		this.wiki = wiki;
		load_mgr = new Xodb_load_mgr_txt(wiki);
		save_mgr = new Xodb_save_mgr_txt(wiki, load_mgr);
	}	private Xowe_wiki wiki;
	public byte Tid() {return Tid_txt;} public static final byte Tid_txt = 0;
	public String Tid_name() {return "xdat";}
	public byte Data_storage_format() {return data_storage_format;} public void Data_storage_format_(byte v) {data_storage_format = v;} private byte data_storage_format = gplx.core.ios.streams.Io_stream_.Tid_raw;
	public Xodb_load_mgr Load_mgr() {return load_mgr;} private Xodb_load_mgr_txt load_mgr;
	public Xodb_save_mgr Save_mgr() {return save_mgr;} private Xodb_save_mgr_txt save_mgr;
	public DateAdp Dump_date_query() {
		Io_url url = wiki.Tdb_fsys_mgr().Url_ns_fil(Xotdb_dir_info_.Tid_page, Xow_ns_.Tid__main, 0);
		return Io_mgr.Instance.QueryFil(url).ModifiedTime();
	}
	public byte Category_version() {
		if (category_version == Xoa_ctg_mgr.Version_null) {
			if (Io_mgr.Instance.ExistsDir(wiki.Tdb_fsys_mgr().Url_site_dir(Xotdb_dir_info_.Tid_category2_link)))
				category_version = Xoa_ctg_mgr.Version_2;
			else
				category_version = Xoa_ctg_mgr.Version_1;
		}
		return category_version;
	}	byte category_version = Xoa_ctg_mgr.Version_null;
	public byte Search_version() {return gplx.xowa.addons.wikis.searchs.specials.Srch_special_page.Version_2;}
	public void Search_version_refresh() {throw Err_.new_unimplemented();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_data_storage_format))				return Io_stream_.Obsolete_to_str(data_storage_format);
		else if	(ctx.Match(k, Invk_data_storage_format_))				data_storage_format = Io_stream_.Obsolete_to_tid(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_category_version))					return this.Category_version();
		else if	(ctx.Match(k, Invk_category_version_))					category_version = m.ReadByte("v");
		else if	(ctx.Match(k, Invk_search_version))						return this.Search_version();
		else if	(ctx.Match(k, Invk_tid_name))							return Tid_name();
		return this;
	}
	public static final String
	  Invk_data_storage_format = "data_storage_format", Invk_data_storage_format_ = "data_storage_format_"
	, Invk_category_version = "category_version", Invk_category_version_ = "category_version_"
	, Invk_search_version = "search_version"
	, Invk_tid_name = "tid_name"
	;
}
