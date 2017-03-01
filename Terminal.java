package visual;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.util.Scanner;

import data.MatchPackage;
import match.Match;

public class Terminal {

	private static int limit = 5;

	public static void main(String[] args) {

		if (SystemTray.isSupported()) {
			Scanner in = new Scanner(System.in);
			System.out.print("When do you want to be reminded? (In minutes)");
			limit = Integer.parseInt(in.next());
			in.close();
		} else {
			System.exit(0);
		}

		MatchPackage pack = new MatchPackage();

		while (true) {

			try {

				pack.update();
				System.out.println("Updating package");

				for (Match match : pack.getMatchList()) {

					System.out.println(match);
					if (match.getTime().contains("minutes from now")) {
						int time = Integer.parseInt((match.getTime().split(" minutes from now")[0]));

						if (time == limit) {

							System.out.println("Match incomming soon >> " + match.toString());
							String match_string = match.getTeam1Name() + " vs " + match.getTeam2Name() + " > "
									+ match.getTime();
							displayTray("Arabela", match_string);

						}
					}
				}
	
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void displayTray(String capital, String message) {

		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		TrayIcon trayIcon = new TrayIcon(image, "Arabela");
		trayIcon.setImageAutoSize(true);
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trayIcon.displayMessage(capital, message, MessageType.INFO);
	}
}
