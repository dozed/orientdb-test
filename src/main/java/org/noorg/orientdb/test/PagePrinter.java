package org.noorg.orientdb.test;

import org.noorg.orientdb.test.domain.Page;

public class PagePrinter {

	public static void print(Page page) {
		print(page, 0);
		System.out.println("");
	}
	
	private static void print(Page page, int level) {
		for (int i = 0; i < level; i++ ) {
			System.out.print("--");
		}
		System.out.println(page);
		for (Page p : page.getSubPages()) {
			print(p, level+1);
		}
	}
	
}
