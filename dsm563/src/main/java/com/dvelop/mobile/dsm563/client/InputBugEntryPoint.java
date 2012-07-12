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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort.DENSITY;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MSearchBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

/**
 * @author ckue
 *
 */
public class InputBugEntryPoint implements EntryPoint {

	Logger log = Logger.getLogger(InputBugEntryPoint.class.getName());

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

		//AnimatableDisplay display = GWT.create(AnimatableDisplay.class);
		LayoutPanel main = new LayoutPanel();
		ScrollPanel scroller = new ScrollPanel();
		scroller.setScrollingEnabledX(false);
		MSearchBox searchBox = new MSearchBox();
		scroller.add(searchBox);
		main.add(scroller);
		//display.setFirstWidget(main);

		RootPanel.get().add(main);
	}

}
