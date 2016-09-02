package cn.jsprun.domain;
public class Itempool  implements java.io.Serializable {
	private static final long serialVersionUID = 6855078310342253804L;
	private Short id;			
     private Byte type;			
     private String question;	
     private String answer;		
    public Itempool() {
    }
    public Itempool(Byte type, String question, String answer) {
        this.type = type;
        this.question = question;
        this.answer = answer;
    }
    public Short getId() {
        return this.id;
    }
    public void setId(Short id) {
        this.id = id;
    }
    public Byte getType() {
        return this.type;
    }
    public void setType(Byte type) {
        this.type = type;
    }
    public String getQuestion() {
        return this.question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}