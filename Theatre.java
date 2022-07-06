package com.company;
import java.util.*;


/*Opera house booking system
*
*
* @Author Silvio Stoykov
* @Date 7/5/2022
* */

public class Theatre {
    private final String theatreName;
    private List<Seat> seats = new ArrayList<>();


    //retrieving theatre's name
    public String getTheatreName() {
        return theatreName;
    }


    public Theatre(String theatreName, int numRows, int seatsPerRow) {
        this.theatreName = theatreName; //initializing theatre's name
        //row must be in format 'A01, A02, B01, etc.
        int lastRow = 'A' + (numRows - 1);
        for (char row = 'A'; row <= lastRow; row++){ //iterating through all the rows
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++){ //iterating from seatNum = 1 to the last seat of the row
                //seatNumber must be a string
                //seatNumber must be in format 'A01, A02, B01, etc.
                //%02d properly formats the integer to a String
                Seat seat = new Seat(row + String.format("%02d", seatNum));
                //adding the seats to the Arraylist
                seats.add(seat);
            }
        }
    }

    public boolean reserveSeat(String seatNumber){
        Seat requestedSeat = new Seat(seatNumber);
        //implementing a binary search
        //we are checking for the requestedSeat in the seats ArrayList
        //NOTE: Collections.binarySearch method is rewritten on line 65

        int foundSeat = Collections.binarySearch(seats, requestedSeat, null);

        if(foundSeat >= 0){
            return seats.get(foundSeat).reserve();
        }
        else{
            System.out.println("There is no seat "+ seatNumber);
            return false;
        }

    }

    //printing out all the seats in the theatre
    public void getSeats(){
        for(Seat seat: seats){
            System.out.println(seat.getSeatNumber());
        }
    }

    //inner implementation of the binary search algorithm
    //!!!ALWAYS MAKE SURE TO USE THE INTEGRATED INTO THE COLLECTIONS FRAMEWORK BINARY SEARCH FUNCTION

    public boolean binarySearch(String seatNumber){
        int high, low, mid;
        low = 0;
        high = seats.size()-1;
        mid = 0;
        while(low <= high) {

            mid = (low + high) / 2;
            Seat midSeat = seats.get(mid);
            int compare = midSeat.getSeatNumber().compareTo(seatNumber);
            if (compare < 0) {
                low = mid + 1;
            } else if (compare > 0) {
                high = mid - 1;
            } else {
                return seats.get(mid).reserve();
            }

            //one dot represents one searching iteration
            System.out.println(".");
        }

        System.out.println("No seat " + seatNumber);
        return false;
    }

    private class Seat implements Comparable<Seat>{
        private String seatNumber;
        private boolean reserved = false;

        public Seat(String seatNumber) {
            this.seatNumber = seatNumber;
        }

        @Override
        public int compareTo(Seat seat) {
            return this.seatNumber.compareToIgnoreCase(seat.getSeatNumber()); //returns 0 if equal or 1 if it is greater
        }



        //checks if the seat is reserved
        //if it is not, it will mark it as reserved
        //if it is already reserved, it will prompt the user a message
        public boolean reserve(){
            if(!this.reserved){ //if reserved == false
                this.reserved = true; //reserving the seat
                System.out.println("Seat " + seatNumber + " reserved.");
                return true;
            }
            else{
                return false;
            }
        }

        //cancel() is essentially the opposite of reserve()
        //if the seat is reserved, it will mark it as unreserved
        //if the seat is unreserved, it will return false, because one cannot cancel an unreserved seat
        public boolean cancel(){
            if(this.reserved){ //if reserved == true
                this.reserved = false; //cancel reservation
                System.out.println("Your reservation " + seatNumber + " cancelled");
                return true;
            }else{
                return false;
            }

        }

        //getting the number of a particular seat
        public String getSeatNumber() {
            return seatNumber;
        }
    }

}
