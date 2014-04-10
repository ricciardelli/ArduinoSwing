/*
 * Copyright 2014 Richard Ricciardelli
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ricciardelli.arduino.swing;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

import com.ricciardelli.arduino.Arduino;

/**
 * 
 * Renders the application main screen.
 * 
 * @author Richard Ricciardelli
 * @version 1.0
 * @since 1.7
 * @see Arduino
 */
public class MainScreen {

	/**
	 * The application frame.
	 */
	private JFrame frame;

	/**
	 * Arduino instance.
	 */
	private Arduino arduino = new Arduino("/dev/ttyACM0");

	/**
	 * First LED which can be activated.
	 */
	private static final int FIRST_LED = 4;

	/**
	 * Last LED which can be activated.
	 */
	private static final int LAST_LED = 8;

	/**
	 * ON string value.
	 */
	private static final String ON = " ON";

	/**
	 * OFF string value.
	 */
	private static final String OFF = " OFF";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 650, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 4));

		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					arduino.sendData("0");
					arduino.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		// Rendering the toggle buttons.
		for (int i = FIRST_LED; i <= LAST_LED; i++) {
			final JToggleButton led = new JToggleButton("LED " + i);
			led.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					String actionCommand = event.getActionCommand();
					led.setText(actionCommand + (led.isSelected() ? ON : OFF));
					led.setActionCommand(actionCommand);
					try {
						arduino.sendData(actionCommand.substring(actionCommand.length() - 1));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			frame.getContentPane().add(led);
		}

		// Rendering the switch all button.
		JButton switchAll = new JButton("SWITCH ALL");
		switchAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i <= FIRST_LED; i++)
					((JToggleButton) frame.getContentPane().getComponent(i)).doClick();
			}
		});

		frame.getContentPane().add(switchAll);

		// Rendering the turn on all button.
		JButton turnOnAll = new JButton("TURN ON ALL");
		turnOnAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					arduino.sendData("1");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i <= FIRST_LED; i++) {
					JToggleButton led = ((JToggleButton) frame.getContentPane().getComponent(i));
					String actionCommand = led.getActionCommand();
					led.setText(actionCommand + ON);
					led.setSelected(Boolean.TRUE);
					led.setActionCommand(actionCommand);
				}
			}
		});

		frame.getContentPane().add(turnOnAll);

		// Rendering the turn off all button.
		JButton turnOffAll = new JButton("TURN OFF ALL");
		turnOffAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					arduino.sendData("0");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i <= FIRST_LED; i++) {
					JToggleButton led = ((JToggleButton) frame.getContentPane().getComponent(i));
					String actionCommand = led.getActionCommand();
					led.setText(actionCommand + OFF);
					led.setSelected(Boolean.FALSE);
					led.setActionCommand(actionCommand);
				}
			}
		});

		frame.getContentPane().add(turnOffAll);
	}

}
