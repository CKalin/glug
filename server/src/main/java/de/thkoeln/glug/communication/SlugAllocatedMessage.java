package de.thkoeln.glug.communication;

import de.thkoeln.glug.data.SlugAllocation;

public class SlugAllocatedMessage {
	private String action = "SLUG_ALLOCATED";
	private SlugAllocation slugAllocation;

	public SlugAllocatedMessage(SlugAllocation allocation) {
		this.slugAllocation = allocation;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public SlugAllocation getSlugAllocation() {
		return slugAllocation;
	}
	public void setSlugAllocation(SlugAllocation slugAllocation) {
		this.slugAllocation = slugAllocation;
	}


}
