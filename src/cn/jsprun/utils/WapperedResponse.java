package cn.jsprun.utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
public class WapperedResponse extends HttpServletResponseWrapper {
	private ByteArrayOutputStream buffer = null;
	private ServletOutputStream out = null;
	private PrintWriter writer = null;
	public WapperedResponse(HttpServletResponse resp) throws IOException {
		super(resp);
		buffer = new ByteArrayOutputStream();
		out = new WapperedOutputStream(buffer);
		writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
	}
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return out;
	}
	@Override
	public PrintWriter getWriter() throws UnsupportedEncodingException {
		return writer;
	}
	@Override
	public void flushBuffer() throws IOException {
		if (out != null) {
			out.flush();
			out.close();
		}
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}
	@Override
	public void reset() {
		buffer.reset();
	}
	public String getStringData() throws IOException {
		flushBuffer();
		return buffer.toString(this.getCharacterEncoding());
	}
	public byte[] getByteData() throws IOException {
		flushBuffer();
		return buffer.toByteArray();
	}
	private class WapperedOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream bos = null;
		public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
			bos = stream;
		}
		@Override
		public void write(int b) throws IOException {
			bos.write(b);
		}
	}
}