//http://stackoverflow.com/questions/18800717/convert-text-content-to-image

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//=============================================================================
//=============================================================================
public class TextToGraphics
{
//=============================================================================
	public static void main(String[] args)
	{
		TextToGraphics ttg=new TextToGraphics();
		ttg.saveImageToFile(
			ttg.renderString("hello world\nfoo\nbar   q")
			,"out.png","png"
		);
	}

//=============================================================================
	public TextToGraphics(){}

//=============================================================================
	public void saveImageToFile(BufferedImage img, String filename, String filetype)
	{
		try
		{
			ImageIO.write(img, filetype, new File(filename));
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

//=============================================================================
	public BufferedImage renderString(String text)
	{
		return renderString(text, "Arial", 24, Font.PLAIN, Color.BLACK, null, 0, 0, 0, 0);
	}

//=============================================================================
	public BufferedImage renderString(String text, String fontName, int fontSize, int fontStyle, Color fontColor, Color backgroundColor, int width, int height, int x, int y)
	{
		/*
		Because font metrics is based on a graphics context, we need to create
		a small, temporary image so we can ascertain the width and height
		of the final image
		*/
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = img.createGraphics();
		//Font font = new Font("Arial", Font.PLAIN, 48);
		Font font = new Font(fontName, fontStyle, fontSize);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();

		if(width==0) //auto
		{
			width = fm.stringWidth(text);
		}
		if(height==0) //auto
		{
			height = fm.getHeight();
		}
		g2d.dispose();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		fm = g2d.getFontMetrics();

		if(backgroundColor!=null)
		{
			g2d.setColor(backgroundColor);
			g2d.fillRect(0, 0, width, height);
		}

		g2d.setColor(fontColor);
		//g2d.drawString(text, 0, fm.getAscent());
		for(String line : text.split("\n"))
		{
			g2d.drawString(line, x, y += fm.getHeight());
		}
		g2d.dispose();
		return img;
	}
}//end class TextToGraphics
