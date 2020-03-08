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
package gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.Store; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.Wikibase.*; import gplx.xowa.mediawiki.extensions.Wikibase.lib.*; import gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.stores.*;
import gplx.xowa.xtns.wbases.*;
public class XomwEntityRetrievingTermLookup {
	private final    Wbase_doc_mgr entity_mgr;
	public XomwEntityRetrievingTermLookup(Wbase_doc_mgr entity_mgr) {
		this.entity_mgr = entity_mgr;
	}

	public byte[] getLabel_or_null(byte[] entityId, byte[] languageCode) {
		Wdata_doc entity = entity_mgr.Get_by_xid_or_null(entityId);
		return entity.Get_label_bry_or_null(languageCode);
	}
}