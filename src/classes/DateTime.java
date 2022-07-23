package classes;

import java.util.Calendar;

public class DateTime implements Comparable<DateTime> {

	private int year;
	private int month;
	private int date;

	private int hours;
	private int minutes;
	private int seconds;

	public DateTime(int year, int month, int date, int hours, int minutes, int seconds) {
		super();
		this.year = year;
		this.month = month;
		this.date = date;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public DateTime(String dateTimeString) {
		super();

		try {
			String[] arr = dateTimeString.split("-");
			String[] dateString = arr[0].split("/");
			String[] timeString = arr[1].split(":");

			this.year = Integer.parseInt(dateString[0]);
			this.month = Integer.parseInt(dateString[1]);
			this.date = Integer.parseInt(dateString[2]);
			this.hours = Integer.parseInt(timeString[0]);
			this.minutes = Integer.parseInt(timeString[1]);
			this.seconds = Integer.parseInt(timeString[2]);

		} catch (Exception e) {
			System.out.println("Setting default date/time");

			this.year = Calendar.getInstance().get(Calendar.YEAR);
			this.month = Calendar.getInstance().get(Calendar.MONTH);
			this.date = Calendar.getInstance().get(Calendar.DATE);
			this.hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			this.minutes = Calendar.getInstance().get(Calendar.MINUTE);
			this.seconds = Calendar.getInstance().get(Calendar.SECOND);
		}
	}

	public DateTime() {
		super();
		this.year = Calendar.getInstance().get(Calendar.YEAR);
		this.month = Calendar.getInstance().get(Calendar.MONTH);
		this.date = Calendar.getInstance().get(Calendar.DATE);
		this.hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		this.minutes = Calendar.getInstance().get(Calendar.MINUTE);
		this.seconds = Calendar.getInstance().get(Calendar.SECOND);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		if (Math.log10(year) + 1 > 4) {
			System.out.println("Invalid input for a year.");
		} else {
			this.year = year;
		}
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		if (month > 0 && month < 12) {
			this.month = month;
		} else {
			throw new IllegalArgumentException("Invalid input for a month");
		}

	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		if (getYear() % 400 == 0 && getMonth() == 2) {
			if (date < 30) {
				this.date = date;
			} else {
				throw new IllegalArgumentException("This is a leap year and this "
						+ "month has only 29 days.");
			}
		} else if (!(getYear() % 400 == 0) && getMonth() == 2) {
			if (date < 29) {
				this.date = date;
			} else {
				throw new IllegalArgumentException("Invalid input for a date");
			}
		} else {
			this.date = date;
		}
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		if (hours > 0 && hours < 24) {
			this.hours = hours;
		} else {
			throw new IllegalArgumentException("Invalid input for a hour");
		}
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		if (minutes < 60) {
			this.minutes = minutes;
		} else {
			throw new IllegalArgumentException("Invalid input for minutes");
		}
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		if (seconds < 60) {
			this.seconds = seconds;
		} else {
			throw new IllegalArgumentException("Invalid input for  seconds");
		}
	}

	private long getSecondInaDay() {
		return this.year * 365 * 24 * 60 * 60 +
				this.month * 30 * 24 * 60 * 60 + this.date * 24 * 60 * 60 + this.hours * 60 * 60 +
				this.minutes * 60 + this.seconds;
	}

	public static boolean isSameDate(DateTime dateTime, DateTime givenDate) {
		return givenDate.getYear() == dateTime.getYear() &&
				givenDate.getMonth() == dateTime.getMonth() &&
				givenDate.getDate() == dateTime.getDate();
	}

	@Override
	public int compareTo(DateTime o) {
		return (int) (this.getSecondInaDay() - o.getSecondInaDay());
	}

	@Override
	public String toString() {
		return this.date + "/" + this.month + "/" + this.year + "-" + this.hours + ":" + this.minutes + ":"
				+ this.seconds;
	}
}
