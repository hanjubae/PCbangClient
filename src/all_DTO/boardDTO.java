package all_DTO;

public class boardDTO {
   
   // 게시글 번호, 내용, 작성자, 게시날짜 
   
   private int seq;
   private String id;
   private String title;   // 제목
   private String content;   // 내용
   private String wdate;   // 작성일
   private String pword;   // 작성일
   private String select;
   private String str;
   //private int readcount;   // 몇번
   

   public String getStr() {
      return str;
   }

   public void setStr(String str) {
      this.str = str;
   }

   public boardDTO() {}

   public boardDTO(String select, int seq, String id, String title, String content, String wdate, String pword) {
      super();
      this.select =select;
      this.seq = seq;
      this.id = id;
      this.title = title;
      this.content = content;
      this.wdate = wdate;
      this.pword = pword;
   
   }
   public String getSelect() {
      return select;
   }

   public void setSelect(String select) {
      this.select = select;
   }
   public int getSeq() {
      return seq;
   }

   public void setSeq(int seq) {
      this.seq = seq;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getWdate() {
      return wdate;
   }

   public void setWdate(String wdate) {
      this.wdate = wdate;
   }

   public String getPword() {
      return pword;
   }

   public void setPword(String pword) {
      this.pword = pword;
   }

   
   @Override
   public String toString() {
      return "bbsDTO [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", wdate=" + wdate + ", pw" + pword 
            +"]";
   }
   
   public String filetoString() {
      return seq + "-" + id + "-" + title + "-" + content + "-" + wdate;
   }

}