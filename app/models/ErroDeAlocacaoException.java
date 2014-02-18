package models;

public class ErroDeAlocacaoException extends Exception {

	private String msg;

	public ErroDeAlocacaoException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
