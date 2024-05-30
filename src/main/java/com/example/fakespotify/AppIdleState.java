package com.example.fakespotify;

public class AppIdleState extends State{

    App app = null ;
    AppIdleState() {
        super(StateType.WAITING);
    }

    public void WhileRunning()
    {
//        System.out.println("APP IS IDLE!");
        if(app.playList != null)
            app.stateMachine.ChangeState(app.waitingForPlaylistToFinishState);
    }
}
