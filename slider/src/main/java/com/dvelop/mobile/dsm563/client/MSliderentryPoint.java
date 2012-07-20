/*
 * Copyright (c) by d.velop AG. All Rights Reserved.
 * 
 * Diese Datei ist eine vertrauliche und geschützte Information der d.velop AG.
 * Das Veröffentlichen und Nutzen dieser Informationen darf nur in Übereinstimmung
 * mit den Lizenzbestimmungen der d.velop AG geschehen.
 * 
 * This file is confidential and protected information of the d.velop AG. 
 * Publishing and use of this information may happen only according to the 
 * licence regulations of the d.velop AG.
 *
 * @(#)date of creation: 12.07.2012
 * @(#)time of creation: 11:03:10
 * @(#)user of creation: ckue
 * 
 * @(#)Subversion Infos
 *
 * $Id$
 * $HeadURL$
 * $LastChangedBy$
 * $LastChangedRevision$
 * $LastChangedDate$
 * $Author$
 *
 */
package com.dvelop.mobile.dsm563.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort.DENSITY;
import com.googlecode.mgwt.ui.client.widget.MSlider;

/**
 * @author ckue
 *
 */
public class MSliderentryPoint implements EntryPoint {

	private static final int MAX = 2;
	Logger log = Logger.getLogger(MSliderentryPoint.class.getName());

	@Override
	public void onModuleLoad() {

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable arg0) {
				String s = "";
				try {
					s = buildStackTrace(arg0, "Ein unerwarteter Fehler ist aufgetreten:\n");

					log.log(Level.WARNING, arg0.getMessage(), arg0);
					log.log(Level.WARNING, s);

				} catch (Exception e) {
					// To prevent endless Exception-loops
					Window.alert("UncaughtExceptionHandler " + s);
				}
			}

			private String buildStackTrace(Throwable t, String log) {

				if (t != null) {
					log += t.getClass().toString();
					log += t.getMessage();
					//
					StackTraceElement[] stackTrace = t.getStackTrace();
					if (stackTrace != null) {
						StringBuffer trace = new StringBuffer();

						for (int i = 0; i < stackTrace.length; i++) {
							trace.append(stackTrace[i].getClassName() + "." + stackTrace[i].getMethodName() + "(" + stackTrace[i].getFileName() + ":" + stackTrace[i].getLineNumber());
						}

						log += trace.toString();
					}
					//
					Throwable cause = t.getCause();
					if (cause != null && cause != t) {

						log += buildStackTrace(cause, "CausedBy:\n");

					}
				}
				return log;
			}
		});

		Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				boot();
			}
		};
		Scheduler.get().scheduleDeferred(command);
	}

	public void boot() {
		ViewPort viewPort = new MGWTSettings.ViewPort();
		viewPort.setTargetDensity(DENSITY.MEDIUM).setWidthToDeviceWidth();
		viewPort.setUserScaleAble(false).setMinimumScale(1.0).setMinimumScale(1.0).setMaximumScale(1.0);

		MGWTSettings mGWTSettings = new MGWTSettings();
		mGWTSettings.setViewPort(viewPort);
		mGWTSettings.setIconUrl("logo.png");
		mGWTSettings.setAddGlosToIcon(true);
		mGWTSettings.setFullscreen(true);
		mGWTSettings.setPreventScrolling(true);
		MGWT.applySettings(mGWTSettings);


		MSlider slider = new MSlider();
		slider.setMax(MAX); // (Values: 0 ... MAX-1)
		slider.setValue(MAX - 1); // MAX-1 -> Slider at most right position

		slider.addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				log.info("value: " + event.getValue());
				if (event.getValue() == MAX) {
					Window.alert("Value == MAX");
				}
			}
		});

		RootPanel.get().add(slider);

	}

}
