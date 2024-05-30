package com.example.fakespotify;

public abstract class ClassWithStates {
    StateMachine stateMachine ;
    Thread runThread ;

    boolean shouldStop = false ;
    State startingState ;
    public ClassWithStates(State startingState)
    {
        this.startingState = startingState ;
        stateMachine = new StateMachine(startingState) ;
    }
    public void BeforeExecute()
    {

    }
    public void Execute()
    {
        runThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while(shouldStop == false && stateMachine.currentState.name != StateType.DONE) {
                        stateMachine.currentState.WhileRunning();
                        //System.out.println(stateMachine.currentState.name);
                        Thread.sleep(1000); // Wait for state change
                    }
                    //System.out.println("The entity has finished!");
                }
                catch (IllegalArgumentException e1)
                {
                    System.out.println("The number of milliseconds is negative.");
                }
                catch (InterruptedException e2)
                {
                    System.out.println("The thread has been interrupted.");
                }
            }
        }) ;
        runThread.start();
    }
}
