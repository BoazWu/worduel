package io.worduel.Components;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NameComponent extends HorizontalLayout{
	private H4 readyStatus;
	private H4 name;
	public NameComponent(String name, boolean readyStatus) {
		this.readyStatus = new H4(readyStatus ? "Ready" : "Not Ready");
		this.name = new H4(name);
		add(
		this.name,
		this.readyStatus
		);
	}
	public void setReadyStatus(boolean readyStatus) {
		this.readyStatus.setText(readyStatus ? "Ready" : "Not Ready");
	}
	public void setName(String name) {
		this.name.setText(name);
	}
}
