package org.apache.maven;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyMessage {
    public String getTodaysMessage() throws IOException {
        LocalDate today = LocalDate.now(Config.MY_TIMEZONE);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd");
        String todaysDate = today.format(dateFormatter);
        Wttr.WttrResult wttr = new Wttr().getTodaysWeather();
        String affirmation = new Affirmation().getAffirmation();

        return String.format(
            "%s Good Morning %s 🌡%s\n" +
            "It's %s %s\n" +
            "%s\n" +
            "🌈 Have a Great Day 🌈",
            wttr.weather, wttr.weather, wttr.temperature, 
            todaysDate, wttr.moonPhase,
            affirmation
        );
    }

    public static void main(String[] args) throws IOException {
        String todaysMessage = new DailyMessage().getTodaysMessage();
        System.out.println(todaysMessage);
        new TwilioSMSSender().sendMessage(todaysMessage);
    }
}