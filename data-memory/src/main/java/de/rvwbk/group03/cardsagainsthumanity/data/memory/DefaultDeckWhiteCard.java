package de.rvwbk.group03.cardsagainsthumanity.data.memory;

import de.rvwbk.group03.cardsagainsthumanity.data.WhiteCard;
import de.rvwbk.group03.cardsagainsthumanity.data.WhiteCardHolder;

public enum DefaultDeckWhiteCard implements WhiteCard {
	CARD001,
	CARD002,
	CARD003,
	CARD004,
	CARD005,
	CARD006,
	CARD007,
	CARD008,
	CARD009,
	CARD010,
	CARD011,
	CARD012,
	CARD013,
	CARD014,
	CARD015,
	CARD016,
	CARD017,
	CARD018,
	CARD019,
	CARD020,
	CARD021,
	CARD022,
	CARD023,
	CARD024,
	CARD025,
	CARD026,
	CARD027,
	CARD028,
	CARD029,
	CARD030,
	CARD031,
	CARD032,
	CARD033,
	CARD034,
	CARD035,
	CARD036,
	CARD037,
	CARD038,
	CARD039,
	CARD040,
	CARD041,
	CARD042,
	CARD043,
	CARD044,
	CARD045,
	CARD046,
	CARD047,
	CARD048,
	CARD049,
	CARD050,
	CARD051,
	CARD052,
	CARD053,
	CARD054,
	CARD055,
	CARD056,
	CARD057,
	CARD058,
	CARD059,
	CARD060,
	CARD061,
	CARD062,
	CARD063,
	CARD064,
	CARD065,
	CARD066,
	CARD067,
	CARD068,
	CARD069,
	CARD070,
	CARD071,
	CARD072,
	CARD073,
	CARD074,
	CARD075,
	CARD076,
	CARD077,
	CARD078,
	CARD079,
	CARD080,
	CARD081,
	CARD082,
	CARD083,
	CARD084,
	CARD085,
	CARD086,
	CARD087,
	CARD088,
	CARD089,
	CARD090,
	CARD091,
	CARD092,
	CARD093,
	CARD094,
	CARD095,
	CARD096,
	CARD097,
	CARD098,
	CARD099,
	CARD100,
	CARD101,
	CARD102,
	CARD103,
	CARD104,
	CARD105,
	CARD106,
	CARD107,
	CARD108,
	CARD109,
	CARD110,
	CARD111,
	CARD112,
	CARD113,
	CARD114,
	CARD115,
	CARD116,
	CARD117,
	CARD118,
	CARD119,
	CARD120,
	CARD121,
	CARD122,
	CARD123,
	CARD124,
	CARD125,
	CARD126,
	CARD127,
	CARD128,
	CARD129,
	CARD130,
	CARD131,
	CARD132,
	CARD133,
	CARD134,
	CARD135,
	CARD136,
	CARD137,
	CARD138,
	CARD139,
	CARD140,
	CARD141,
	CARD142,
	CARD143,
	CARD144,
	CARD145,
	CARD146,
	CARD147,
	CARD148,
	CARD149,
	CARD150,
	CARD151,
	CARD152,
	CARD153,
	CARD154,
	CARD155,
	CARD156,
	CARD157,
	CARD158,
	CARD159,
	CARD160,
	CARD161,
	CARD162,
	CARD163,
	CARD164,
	CARD165,
	CARD166,
	CARD167,
	CARD168,
	CARD169,
	CARD170,
	CARD171,
	CARD172,
	CARD173,
	CARD174,
	CARD175,
	CARD176,
	CARD177,
	CARD178,
	CARD179,
	CARD180,
	CARD181,
	CARD182,
	CARD183,
	CARD184,
	CARD185,
	CARD186,
	CARD187,
	CARD188,
	CARD189,
	CARD190,
	CARD191,
	CARD192,
	CARD193,
	CARD194,
	CARD195,
	CARD196,
	CARD197,
	CARD198,
	CARD199,
	CARD200,
	CARD201,
	CARD202,
	CARD203,
	CARD204,
	CARD205,
	CARD206,
	CARD207,
	CARD208,
	CARD209,
	CARD210,
	CARD211,
	CARD212,
	CARD213,
	CARD214,
	CARD215,
	CARD216,
	CARD217,
	CARD218,
	CARD219,
	CARD220,
	CARD221,
	CARD222,
	CARD223,
	CARD224,
	CARD225,
	CARD226,
	CARD227,
	CARD228,
	CARD229,
	CARD230,
	CARD231,
	CARD232,
	CARD233,
	CARD234,
	CARD235,
	CARD236,
	CARD237,
	CARD238,
	CARD239,
	CARD240,
	CARD241,
	CARD242,
	CARD243,
	CARD244,
	CARD245,
	CARD246,
	CARD247,
	CARD248,
	CARD249,
	CARD250,
	CARD251,
	CARD252,
	CARD253,
	CARD254,
	CARD255,
	CARD256,
	CARD257,
	CARD258,
	CARD259,
	CARD260,
	CARD261,
	CARD262,
	CARD263,
	CARD264,
	CARD265,
	CARD266,
	CARD267,
	CARD268,
	CARD269,
	CARD270,
	CARD271,
	CARD272,
	CARD273,
	CARD274,
	CARD275,
	CARD276,
	CARD277,
	CARD278,
	CARD279,
	CARD280,
	CARD281,
	CARD282,
	CARD283,
	CARD284,
	CARD285,
	CARD286,
	CARD287,
	CARD288,
	CARD289,
	CARD290,
	CARD291,
	CARD292,
	CARD293,
	CARD294,
	CARD295,
	CARD296,
	CARD297,
	CARD298,
	CARD299,
	;
	
	private final String text;
	private WhiteCardHolder whiteCardHolder;
	
	private DefaultDeckWhiteCard() {
		this.text = "White Card " + ordinal();
	}
	
	@Override
	public int getId() {
		return ordinal();
	}
	
	@Override
	public String getText() {
		return this.text;
	}
	
	@Override
	public void setCardHolder(final WhiteCardHolder whiteCardHolder) {
		if (this.whiteCardHolder != null) {
			this.whiteCardHolder.removeWhiteCard(this);
		}
		
		this.whiteCardHolder = whiteCardHolder;
		
		if(this.whiteCardHolder != null) {
			this.whiteCardHolder.addWhiteCard(this);
		}
	}
}
