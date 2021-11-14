class DequeDblLinkedList<T> {
    
    private Node<T> front;
    private Node<T> rear;
    
    /*
     * Adds element at the beginning of the queue
    */
    public void enqueueFront(T item){
        System.out.println("adding at front: "+item);
        //create new node
        Node<T> newFront = new Node<T>();
        //set the value;
        newFront.setValue(item);
        //set the link to the next node
        newFront.setNext(front);
        //check the first node and take action
        if (this.front == null) {
            this.rear = newFront;
            this.front = newFront;
        }       
        else {
            front.setPrev(newFront);
            this.front = newFront;
        }
    }
    
    /*
     * Adds element at the end of the queue
    */
    public void enqueueRear(T item){
        System.out.println("adding at rear: "+item);
        //create new node
        Node<T> newRear = new Node<T>();
        //set the value
        newRear.setValue(item);
        //set the link to the previous node
        newRear.setPrev(rear);
        //check the last node and take action
        if (this.rear == null){
            this.front = newRear;
            this.rear = newRear;
        }
        else {
            this.rear.setNext(newRear);
            this.rear = newRear;
        }
    }
     
    /*
     * Remove an item from the beginning of the queue
    */
    public void dequeueFront(){
        if(front == null){
            System.out.println("Deque underflow!! unable to remove.");
            return;
        }
        else{
        Node<T> newFront  = front.getNext();
        if (newFront != null) {
            newFront.setPrev(null);
            this.front  = newFront;   
            } 
            else {
                this.front = null;
                this.rear = null;
            } 
        }    
    }
     
    /*
     * Remove an item from the rear of the queue
    */
    public void dequeueRear(){
        if(rear == null){
            System.out.println("Deque underflow!! unable to remove.");
            return;
        }
        else{ 
            Node<T> newRear = rear.getPrev();
            if (newRear != null){
                newRear.setNext(null);
                this.rear = newRear;
            }
            else{ 
                this.rear = null;
                this.front = null;
            }
        }
    }
}