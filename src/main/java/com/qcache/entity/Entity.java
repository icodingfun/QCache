package com.qcache.entity;

/**
 * Created by hunter on 3/2/16.
 */
public abstract class Entity implements java.io.Serializable {
	private boolean saveable;

	/**
	 * @return the saveable
	 */
	public boolean isSaveable() {
		return saveable;
	}

	/**
	 * @param saveable
	 *            the saveable to set
	 */
	public void setSaveable(boolean saveable) {
		this.saveable = saveable;
	}

	public void saveToDB() {
//		if (isSaveable()) {
//			// do save here ...
//			// TODO ...
//		}
	}
}
