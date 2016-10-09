package bridgempp.message.formats;

public class Base64EncodedMessageBody extends PlainTextMessageBody
{

	public Base64EncodedMessageBody(String text)
	{
		super(text);
	}

	
	@Override
	public String getFormatName()
	{
		return "Base64 Encoded";
	}
}
