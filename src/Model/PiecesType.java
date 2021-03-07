package Model;

public enum PiecesType {

    BLACK(1), WHITE(2), KING_BLACK(-1), KING_WHITE(-2), NOTHING(0);

    int typeOfPiece;

    // constructor not needed, but was for the changing from an int matrix to a Model.PiecesType Matrix
    PiecesType(int typeOfPiece){
        this.typeOfPiece = typeOfPiece;
    }

}
