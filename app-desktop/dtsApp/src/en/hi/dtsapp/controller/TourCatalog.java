package en.hi.dtsapp.controller;

import en.hi.dtsapp.model.Tour;
import en.hi.dtsapp.model.TourDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class objective
 * Accepts input, optionally validates it, and converts it to commands for the model or view.
 * 
 * 
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * @date
 * Háskóli Íslands
 */
public class TourCatalog {
    
    private final List<Tour> TOUR_LIST;
    private final List<Tour> DISTINCT_NAME_TOUR_LIST;
//    private final ObservableList<Tour> observableTourList;
    
    public TourCatalog() throws Exception {
        this.TOUR_LIST = TourDAO.initiateTourCatalog();
        this.DISTINCT_NAME_TOUR_LIST = TourDAO.distinctTourCatalog();
 //       observableTourList =  FXCollections.observableArrayList(TOUR_LIST);
    }
    
    public ObservableList<Tour> getFullTourList() {
        return FXCollections.observableArrayList(TOUR_LIST);
    }
    
    public ObservableList<Tour> getDistinctNameTourList() {
        return FXCollections.observableArrayList(DISTINCT_NAME_TOUR_LIST);
    }
    
    // Ef ekkert virkar þá skilar þessi this.getFullTourList()
    public ObservableList<Tour> getToursBySearchParameters(
            String kw, String from, String to) {
        ObservableList <Tour> newTourList = this.getFullTourList();
        if(!(kw == null || kw.isEmpty() || kw.contains("Search") || "Type keywords here or pick dates".equals(kw))){
            newTourList = getToursByKeyword(newTourList, kw);
        }
         try{
           newTourList = getToursByDate(newTourList, from, to);
         }
         catch(ParseException pe){
           System.err.println("Parse Exception thrown in TourCatalog.getToursBySearchParameters");
         }
        return newTourList;
    }
    
    private ObservableList<Tour> getToursByKeyword(ObservableList<Tour> fullTourList, String kw) {
            ObservableList <Tour> newTourList = fullTourList.filtered(
                       s -> s.getKeywords().contains(kw) 
                    || s.getName().contains(kw) 
                    || s.getInfo().contains(kw) 
                    || s.getLocation().contains(kw) 
                    || s.getOperator().contains(kw));
            return newTourList;
    }
    
    private Boolean checkBetweenDates(Date from, Date to, Date between){
        return between.after(from) && between.before(to);
    }
    
     private ObservableList<Tour> getToursByDate(
             ObservableList<Tour> fullTourList, String from, String to)
             throws ParseException {
            if(from == null || to == null ){ // Getur ekki lengur gerst í þessari útfærslu
                System.err.println("getToursByDate received a null paremeter...");
                return null; // Gætu hins vegar verið tómir. Tökumst á við það við tækifæri.
            }
            Date date1=new SimpleDateFormat("ddMMyyyy").parse(from);
            Date date2=new SimpleDateFormat("ddMMyyyy").parse(to);
            System.out.println(date1);
            System.out.println(date2);
            ObservableList <Tour> newTourList = FXCollections.observableArrayList();
            for (Tour s : fullTourList) {
                Date dateS = new SimpleDateFormat("ddMMyyyy").parse(s.getDate());
                if(s.getDate().contains(from)){
                    newTourList.add(s);
                } else if(s.getDate().contains(to)) {
                    newTourList.add(s);
                } else if (checkBetweenDates(date1, date2, dateS)){
                    newTourList.add(s);
                }
            } 
            return newTourList;
    }
   
    private void displaySomeTours() {     // Basically a test method
        System.out.println("Displaying some Tours:");
        System.out.println(TOUR_LIST.get(10));
        System.out.println(TOUR_LIST.get(100));
        System.out.println(TOUR_LIST.get(700));
    }
}
