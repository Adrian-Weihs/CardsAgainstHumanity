package de.rvwbk.group03.cardsagainsthumanity.data.memory;

import de.rvwbk.group03.cardsagainsthumanity.data.BlackCard;
import de.rvwbk.group03.cardsagainsthumanity.data.BlackCardHolder;

public enum DefaultDeckBlackCard implements BlackCard {
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
	;
	
	private final String text;
	private final int numberOfGaps;
	private BlackCardHolder cardHolder;
	
	private DefaultDeckBlackCard() {
		this.text = "Black Card " + ordinal();
		this.numberOfGaps = 1 + (int) (Math.random() * 3);
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
	public int getNumberOfGaps() {
		return this.numberOfGaps;
	}
	
	@Override
	public void setCardHolder(final BlackCardHolder cardHolder) {
		if (this.cardHolder != null) {
			this.cardHolder.removeBlackCard(this);
		}
		
		this.cardHolder = cardHolder;
		
		if(this.cardHolder != null) {
			this.cardHolder.addBlackCard(this);
		}
	}
}
