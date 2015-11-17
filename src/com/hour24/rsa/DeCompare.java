package com.hour24.rsa;

public class DeCompare {
	Decode decode = new Decode();
	FakeDe fakecode = new FakeDe();
	private String incoding;
	private String fakecut;
	public String Com(String re) {
		this.fakecut = fakecode.fakedecode(re);
		this.incoding = decode.toTax(fakecut);
		return incoding;
	}

	
}
