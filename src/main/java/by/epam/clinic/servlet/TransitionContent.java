package by.epam.clinic.servlet;

public class TransitionContent {
    private String page;

    private TransitionType transitionType;

    public TransitionContent(String page, TransitionType transitionType) {
        this.page = page;
        this.transitionType = transitionType;
    }
    public void setRedirect() {
        transitionType = TransitionType.REDIRECT;
    }
    public String getPage() {
        return page;
    }

    public TransitionType getTransitionType() {
        return transitionType;
    }
}
