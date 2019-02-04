/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.langs.htmls.docs.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.xowa.wikis.ttls.*; 
public class Xoh_hdoc_wkr__make implements Xoh_hdoc_wkr {
	private Xoh_hzip_bfr bfr; private Xoh_page hpg; private Xoh_hdoc_ctx hctx; private byte[] src;
	private final    Xoh_hdr_wtr wkr__hdr = new Xoh_hdr_wtr();
	private final    Xoh_img_wtr wkr__img = new Xoh_img_wtr();
	private int html_uid;
	public void On_new_page(Xoh_hzip_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr; this.hpg = hpg; this.hctx = hctx; this.src = src;
		this.html_uid = 0;
	}
	public void On_txt(int rng_bgn, int rng_end) {
		// text; just add it
		bfr.Add_mid(src, rng_bgn, rng_end);
	}
	public void On_escape(gplx.xowa.htmls.core.wkrs.escapes.Xoh_escape_data data) {
		// hzip escape byte ((byte)27); should never happen but if it does, add it
		bfr.Add(data.Hook());
	}
	public void On_xnde(gplx.xowa.htmls.core.wkrs.xndes.Xoh_xnde_parser data) {
		// regular xml node; just add it
		bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
	}
	public void On_lnki(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_data data)	{
		// <a> node
		// handle "#"
		if (data.Href_itm().Tid() == Xoh_anch_href_data.Tid__anch) {
			bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
			return;
		}

		// increment html_uid and add "id=xolnki_"
		byte[] ttl_bry = data.Href_itm().Ttl_page_db();
		this.html_uid = Lnki_redlink_reg(hpg, hctx, ttl_bry, html_uid);
		int src_bgn_lhs = data.Src_bgn();
		int src_bgn_rhs = src_bgn_lhs + 3; // +3 to skip over "<a "
		if (Bry_.Match(src, src_bgn_lhs, src_bgn_rhs, Bry__a__bgn)) {
			bfr.Add(Bry__a__id);
			bfr.Add_int_variable(html_uid);
			bfr.Add_byte_quote().Add_byte_space();
			bfr.Add_mid(src, src_bgn_rhs, data.Src_end());
		}
		else {
			bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
			Gfo_usr_dlg_.Instance.Warn_many("", "", "anchor hook should start with <a; url=~{0}", hpg.Url_bry_safe());
		}
	}
	public void On_thm		(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_data data) {
		Xoh_img_data img_data = (gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_data)data.Img_data();
		bfr.Add_mid(src, data.Src_bgn(), img_data.Src_bgn());		
		wkr__img.Init_by_parse(bfr, hpg, hctx, src, img_data);
		bfr.Add_mid(src, img_data.Src_end(), data.Src_end());
	}
	public void On_gly		(gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_grp_data data) {
		bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
		hpg.Xtn__gallery_exists_y_();
	}
	public boolean Process_parse(Xoh_data_itm data) {
		switch (data.Tid()) {
			case Xoh_hzip_dict_.Tid__img:	return wkr__img.Init_by_parse(bfr, hpg, hctx, src, (gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_data)data);
			case Xoh_hzip_dict_.Tid__hdr:	return wkr__hdr.Init_by_parse(bfr, hpg, hctx, src, (gplx.xowa.htmls.core.wkrs.hdrs.Xoh_hdr_data)data);
			case Xoh_hzip_dict_.Tid__lnke:
			default:
				bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
				break;
		}
		return true;
	}
	public static int Lnki_redlink_reg(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] href_bry, int html_uid) {
		if (hctx.Mode_is_diff()) return html_uid; // PERF: don't do redlinks during hzip_diff
		try {
			Xoa_ttl ttl = hpg.Wiki().Ttl_parse(Gfo_url_encoder_.Href.Decode(href_bry));
			Xopg_lnki_itm__hdump lnki_itm = new Xopg_lnki_itm__hdump(ttl);
			hpg.Html_data().Redlink_list().Add(lnki_itm);
			return lnki_itm.Html_uid();
		} 
		catch (Exception e) {
			Gfo_log_.Instance.Warn("failed to add lnki to redlinks", "page", hpg.Url_bry_safe(), "href_bry", href_bry, "e", Err_.Message_gplx_log(e));
			return html_uid;
		}
	}
	private static final    byte[] Bry__a__bgn = Bry_.new_a7("<a "), Bry__a__id = Bry_.new_a7("<a id=\"xolnki_");
}
