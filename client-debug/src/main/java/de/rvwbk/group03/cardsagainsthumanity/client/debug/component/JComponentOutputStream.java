package de.rvwbk.group03.cardsagainsthumanity.client.debug.component;

import java.awt.EventQueue;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JComponent;

public class JComponentOutputStream extends OutputStream {
	
	// *************************************************************************************************
	// INSTANCE MEMBERS
	// *************************************************************************************************
	
	private byte[] oneByte; // array for write(int val);
	private Appender appender; // most recent action
	
	private Lock jcosLock = new ReentrantLock();
	
	
	public JComponentOutputStream(final JComponent txtara, final JComponentHandler handler) {
		this(txtara, 1000, handler);
	}
	
	public JComponentOutputStream(final JComponent txtara, final int maxlin, final JComponentHandler handler) {
		if (maxlin < 1) {
			throw new IllegalArgumentException("JComponentOutputStream maximum lines must be positive (value=" + maxlin + ")");
		}
		this.oneByte = new byte[1];
		this.appender = new Appender(txtara, maxlin, handler);
	}
	
	/** Clear the current console text area. */
	public void clear() {
		this.jcosLock.lock();
		try {
			if (this.appender != null) {
				this.appender.clear();
			}
		} finally {
			this.jcosLock.unlock();
		}
	}
	
	@Override
	public void close() {
		this.jcosLock.lock();
		try {
			this.appender = null;
		} finally {
			this.jcosLock.unlock();
		}
	}
	
	@Override
	public void flush() {
		// sstosLock.lock();
		// try {
		// // TODO: Add necessary code here...
		// } finally {
		// sstosLock.unlock();
		// }
	}
	
	@Override
	public void write(final int val) {
		this.jcosLock.lock();
		try {
			this.oneByte[0] = (byte) val;
			write(this.oneByte, 0, 1);
		} finally {
			this.jcosLock.unlock();
		}
	}
	
	@Override
	public void write(final byte[] ba) {
		this.jcosLock.lock();
		try {
			write(ba, 0, ba.length);
		} finally {
			this.jcosLock.unlock();
		}
	}
	
	@Override
	public void write(final byte[] ba, final int str, final int len) {
		this.jcosLock.lock();
		try {
			if (this.appender != null) {
				this.appender.append(bytesToString(ba, str, len));
			}
		} finally {
			this.jcosLock.unlock();
		}
	}
	
	private static String bytesToString(final byte[] ba, final int str, final int len) {
		try {
			return new String(ba, str, len, "UTF-8");
		} catch (UnsupportedEncodingException thr) {
			return new String(ba, str, len);
		} // all JVMs are required to support UTF-8
	}
	
	
	public static class Appender implements Runnable {
		private final JComponent swingComponent;
		private final int maxLines; // maximum lines allowed in text area
		private final LinkedList<Integer> lengths; // length of lines within
		// text area
		private final List<String> values; // values waiting to be appended
		
		private int curLength; // length of current line
		private boolean clear;
		private boolean queue;
		
		private Lock appenderLock;
		
		private JComponentHandler handler;
		
		
		Appender(final JComponent cpt, final int maxlin, final JComponentHandler hndlr) {
			this.appenderLock = new ReentrantLock();
			
			this.swingComponent = cpt;
			this.maxLines = maxlin;
			this.lengths = new LinkedList<>();
			this.values = new ArrayList<>();
			
			this.curLength = 0;
			this.clear = false;
			this.queue = true;
			
			this.handler = hndlr;
		}
		
		void append(final String val) {
			this.appenderLock.lock();
			try {
				this.values.add(val);
				if (this.queue) {
					this.queue = false;
					EventQueue.invokeLater(this);
				}
			} finally {
				this.appenderLock.unlock();
			}
		}
		
		void clear() {
			this.appenderLock.lock();
			try {
				
				this.clear = true;
				this.curLength = 0;
				this.lengths.clear();
				this.values.clear();
				if (this.queue) {
					this.queue = false;
					EventQueue.invokeLater(this);
				}
			} finally {
				this.appenderLock.unlock();
			}
		}
		
		@Override
		public void run() {
			this.appenderLock.lock();
			try {
				if (this.clear) {
					this.handler.setText(this.swingComponent, "");
				}
				for (String val : this.values) {
					this.curLength += val.length();
					if (val.endsWith(EOL1) || val.endsWith(EOL2)) {
						if (this.lengths.size() >= this.maxLines) {
							this.handler.replaceRange(this.swingComponent, "", 0, this.lengths.removeFirst());
						}
						this.lengths.addLast(this.curLength);
						this.curLength = 0;
					}
					this.handler.append(this.swingComponent, val);
				}
				
				this.values.clear();
				this.clear = false;
				this.queue = true;
			} finally {
				this.appenderLock.unlock();
			}
		}
		
		
		static private final String EOL1 = "\n";
		static private final String EOL2 = System.getProperty("line.separator", EOL1);
	}
	
	public interface JComponentHandler {
		void setText(JComponent swingComponent, String text);
		
		void replaceRange(JComponent swingComponent, String text, int start, int end);
		
		void append(JComponent swingComponent, String text);
	}
	
}
