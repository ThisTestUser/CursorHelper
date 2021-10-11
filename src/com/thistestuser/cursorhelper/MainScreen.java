package com.thistestuser.cursorhelper;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainScreen
{	
	private JFrame frame;
	private JTextField xField;
	private JTextField yField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					MainScreen window = new MainScreen();
					window.frame.setVisible(true);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public MainScreen()
	{
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setTitle("CursorHelper");
		frame.setBounds(100, 100, 475, 175);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(ClassNotFoundException | InstantiationException
			| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		xField = new JTextField();
		GridBagConstraints gbc_xField = new GridBagConstraints();
		gbc_xField.insets = new Insets(5, 5, 5, 5);
		gbc_xField.anchor = GridBagConstraints.NORTHWEST;
		gbc_xField.fill = GridBagConstraints.HORIZONTAL;
		gbc_xField.gridx = 0;
		gbc_xField.gridy = 0;
		gbc_xField.weightx = 0.5;
		frame.getContentPane().add(xField, gbc_xField);
		
		yField = new JTextField();
		GridBagConstraints gbc_yField = new GridBagConstraints();
		gbc_yField.insets = new Insets(5, 5, 5, 5);
		gbc_yField.anchor = GridBagConstraints.NORTHWEST;
		gbc_yField.fill = GridBagConstraints.HORIZONTAL;
		gbc_yField.gridx = 1;
		gbc_yField.gridy = 0;
		gbc_yField.weightx = 0.5;
		frame.getContentPane().add(yField, gbc_yField);
		
		JButton btnSet = new JButton("Set Mouse");
		GridBagConstraints gbc_set = new GridBagConstraints();
		gbc_set.insets = new Insets(5, 5, 5, 5);
		gbc_set.anchor = GridBagConstraints.CENTER;
		gbc_set.gridx = 2;
		gbc_set.gridy = 0;
		btnSet.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int x = Integer.parseInt(xField.getText());
					int y = Integer.parseInt(yField.getText());
					int tempX = x;
					int tempY = y;
					int count = 0;
					int moveX = x * 4/5;
					int moveY = y * 4/5;
					Robot r = new Robot();
					r.mouseMove(0, 0);
					r.mouseMove(x, y);
					Point loc = MouseInfo.getPointerInfo().getLocation();
					while((loc.x != x || loc.y != y) && count < 25)
					{
						if(loc.x > x)
							tempX--;
						else if(loc.x < x)
							tempX++;
						if(loc.y > y)
							tempY--;
						else if(loc.y < y)
							tempY++;
						moveX = tempX * 4/5;
						moveY = tempY * 4/5;
						r.mouseMove(0, 0);
						r.mouseMove(moveX, moveY);
						loc = MouseInfo.getPointerInfo().getLocation();
						count++;
					}
				}catch(Exception ex)
				{
					
				}
			}
		});
		frame.getContentPane().add(btnSet, gbc_set);
		
		JComboBox<WindowContainer> windowBox = new JComboBox<>();
		windowBox.addItem(new WindowContainer("Select a window", null));
		windowBox.setSelectedIndex(0);
		GridBagConstraints gbc_windowBox = new GridBagConstraints();
		gbc_windowBox.insets = new Insets(5, 5, 5, 5);
		gbc_windowBox.gridx = 0;
		gbc_windowBox.anchor = GridBagConstraints.CENTER;
		gbc_windowBox.gridy = 1;
		gbc_windowBox.gridwidth = 2;
		frame.getContentPane().add(windowBox, gbc_windowBox);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				windowBox.removeAllItems();
				windowBox.addItem(new WindowContainer("Select a window", null));
				for(DesktopWindow window : WindowUtils.getAllWindows(true))
					windowBox.addItem(new WindowContainer(window.getTitle(), window.getHWND()));
				windowBox.setSelectedIndex(0);
			}
		});
		GridBagConstraints gbc_refreshButton = new GridBagConstraints();
		gbc_refreshButton.insets = new Insets(5, 5, 5, 5);
		gbc_refreshButton.gridx = 2;
		gbc_refreshButton.gridy = 1;
		frame.getContentPane().add(refreshButton, gbc_refreshButton);
		
		JLabel mouseXY = new JLabel("Mouse X: null Y: null");
		GridBagConstraints gbc_mouseXY = new GridBagConstraints();
		gbc_mouseXY.insets = new Insets(5, 5, 5, 5);
		gbc_mouseXY.gridx = 0;
		gbc_mouseXY.anchor = GridBagConstraints.CENTER;
		gbc_mouseXY.gridy = 2;
		gbc_mouseXY.gridwidth = 3;
		frame.getContentPane().add(mouseXY, gbc_mouseXY);
		
		JLabel windowXY = new JLabel("Window not selected");
		GridBagConstraints gbc_windowXY = new GridBagConstraints();
		gbc_windowXY.gridx = 0;
		gbc_windowXY.anchor = GridBagConstraints.CENTER;
		gbc_windowXY.gridy = 3;
		gbc_windowXY.gridwidth = 3;
		frame.getContentPane().add(windowXY, gbc_windowXY);
		
		Timer timer = new Timer(50, new ActionListener()
		{
			@Override
			public synchronized void actionPerformed(ActionEvent e)
			{
				Point point = MouseInfo.getPointerInfo().getLocation();
				mouseXY.setText("Mouse X: " + point.getX() + " Y: " + point.getY());
            }
        });
		timer.start();
		
		Timer timer2 = new Timer(1000, new ActionListener()
		{
			@Override
			public synchronized void actionPerformed(ActionEvent e)
			{
				if(((WindowContainer)windowBox.getSelectedItem()).hwnd == null)
				{
					windowXY.setText("Window not selected");
					return;
				}
				for(DesktopWindow window : WindowUtils.getAllWindows(true))
				    if(window.getHWND().hashCode() == ((WindowContainer)windowBox.getSelectedItem()).hwnd.hashCode())
				    {
				    	Rectangle rect = window.getLocAndSize();
				    	windowXY.setText("Window X:" + rect.x + " Y:" + rect.y + " H:" + rect.height + " W:" + rect.width);
				    	return;
				    }
				windowXY.setText("Window not found");
            }
        });
		timer2.start();
	}
	
	private class WindowContainer
	{
		private String title;
		public final HWND hwnd;
		
		public WindowContainer(String title, HWND hwnd)
		{
			this.title = title;
			this.hwnd = hwnd;
		}
		
		@Override
		public String toString()
		{
			return title;
		}
	}
}
