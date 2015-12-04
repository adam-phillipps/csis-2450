package battleship;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class GameLogFormatter extends Formatter
{
	@Override
	public String format(LogRecord record)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(record.getMessage());
		sb.append(System.getProperty("line.separator"));
		return sb.toString();

	}
}