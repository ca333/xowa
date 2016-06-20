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
package gplx.xowa.apps.apis.xowa.xtns; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.xtns.wdatas.*;
public class Xoapi_wikibase implements Gfo_invk, Gfo_evt_mgr_owner {
	public Xoapi_wikibase() {
		evt_mgr = new Gfo_evt_mgr(this);
	}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private Gfo_evt_mgr evt_mgr;
	public byte[][] Core_langs() {return core_langs;} private byte[][] core_langs = Bry_.Ary("en");
	public byte[][] Sort_langs() {return sort_langs;} private byte[][] sort_langs = Bry_.Ary("en", "de", "es", "fr", "it", "nl", "pl", "ru", "sv");
	public byte[] Link_wikis() {return link_wikis;} private byte[] link_wikis = Bry_.new_a7("enwiki");
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_core_langs))	 			return Bry_.Add_w_dlm(Byte_ascii.Semic, core_langs);
		else if	(ctx.Match(k, Invk_core_langs_))	 		{core_langs = m.ReadBryAry(k, Byte_ascii.Semic); Gfo_evt_mgr_.Pub_val(this, Evt_core_langs_changed, core_langs);}
		else if	(ctx.Match(k, Invk_sort_langs))	 			return Bry_.Add_w_dlm(Byte_ascii.Semic, sort_langs);
		else if	(ctx.Match(k, Invk_sort_langs_))	 		{sort_langs = m.ReadBryAry(k, Byte_ascii.Semic); Gfo_evt_mgr_.Pub_val(this, Evt_sort_langs_changed, sort_langs);}
		else if	(ctx.Match(k, Invk_link_wikis))	 			return String_.new_u8(link_wikis);
		else if	(ctx.Match(k, Invk_link_wikis_))	 		{link_wikis = m.ReadBry(k); Gfo_evt_mgr_.Pub_val(this, Evt_link_wikis_changed, link_wikis);}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_core_langs = "core_langs", Invk_core_langs_ = "core_langs_"
	, Invk_sort_langs = "sort_langs", Invk_sort_langs_ = "sort_langs_"
	, Invk_link_wikis = "link_wikis", Invk_link_wikis_ = "link_wikis_"
	;
	public static final String 
	  Evt_core_langs_changed = "core_langs_changed"
	, Evt_link_wikis_changed = "link_wikis_changed"
	, Evt_sort_langs_changed = "sort_langs_changed"
	;
}
