package com.example.fakespotify;

public class AppWaitingForPlaylistToFinishState extends State{
    App app = null ;
    AppWaitingForPlaylistToFinishState() {
        super(StateType.WAITING);
    }

    public void SetApp(App app)
    {
        this.app = app ;
    }

    @Override
    public void OnEnter() {
        if(app == null)
            app.stateMachine.ChangeState(app.appIdleState);
        else
        {
            app.playList.BeforeExecute();
            app.playList.stateMachine.ChangeState(app.playList.generatingState);
            app.playList.Execute();
        }
    }

    public void WhileRunning() {
//        System.out.println("APP IS WAITING!");
        if (app.playList == null)
            app.stateMachine.ChangeState(app.appIdleState);
        if (app != null && app.playList.stateMachine.currentState.name == StateType.DONE) {
            app.playList = null ;
            app.stateMachine.ChangeState(app.appIdleState);
        }

    }
}
