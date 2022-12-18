package site.metacoding.junitproject.util;

// 추후에 Mail 클래스가 완성될 때 코드를 수정하면 됨
// @Component
public class MailSenderAdapter implements MailSender {

    // private Mail mail;

    // public MailSenderAdapter() {
    // this.mail = new Mail();
    // }

    @Override
    public boolean send() {
        return true;
    }

}
