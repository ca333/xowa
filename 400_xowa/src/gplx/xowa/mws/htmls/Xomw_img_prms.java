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
package gplx.xowa.mws.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Xomw_img_prms {
	public byte[] align = null;
	public byte[] valign = null;
	public byte[] caption = null;
	public byte[] frame = null;
	public byte[] framed = null;
	public byte[] frameless = null;
	public byte[] thumbnail = null;
	public byte[] manual_thumb = null;
	public byte[] alt = null;
	public byte[] title = null;
	public byte[] cls = null;
	public byte[] img_cls = null;
	public byte[] link_title = null;
	public byte[] link_url = null;
	public byte[] link_target = null;
	public byte[] no_link = null;
	public byte[] border = null;
	public double upright = -1;
	public void Clear() {
		align = valign = caption = frame = framed = frameless
		= thumbnail = manual_thumb = alt = title = cls = img_cls
		= link_title = link_url = link_target = no_link = null;
		upright = -1;
	}
	public static byte[] Cls_add(byte[] lhs, byte[] rhs) {
		return Bry_.Len_eq_0(lhs) ? rhs : Bry_.Add(lhs, Byte_ascii.Space_bry, rhs);
	}
}
