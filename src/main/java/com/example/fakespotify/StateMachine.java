package com.example.fakespotify;

public class StateMachine {
    public State currentState ;
    public void ChangeState(State newState)
    {
        if(currentState != null)
            currentState.OnLeave();
        currentState = newState ;
        if(newState != null)
            newState.OnEnter();
    }

    StateMachine(State currentState)
    {
        this.currentState = currentState ;
    }
}
