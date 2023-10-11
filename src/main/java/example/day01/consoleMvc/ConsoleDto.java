package example.day01.consoleMvc;

import java.time.LocalDate;
// [ p.76 ] 참고
public class ConsoleDto { // TODO 클래스
    private int tno; // TODO 번호
    private String title; // TODO 내용
    private LocalDate dueDate; // TODO 작성일
    private boolean finished; // TODO 실행여부

    public ConsoleDto(){}
    public ConsoleDto(int tno, String title, LocalDate dueDate, boolean finished) {
        this.tno = tno;
        this.title = title;
        this.dueDate = dueDate;
        this.finished = finished;
    }
    public int getTno() {
        return tno;
    }

    public void setTno(int tno) {
        this.tno = tno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ConsoleDto{" +
                "tno=" + tno +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", finished=" + finished +
                '}'+"\n";
    }

}
