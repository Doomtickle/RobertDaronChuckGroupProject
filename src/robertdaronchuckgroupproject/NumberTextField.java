package robertdaronchuckgroupproject;

import javafx.scene.control.TextField;

//This module will ensure users can only enter numbers in text fields

public class NumberTextField extends TextField
{

    @Override
    public void replaceText(int start, int end, String text)
    {
        if (validate(text))
        {   
            //same method that's in the parent method.
            //replaces range of text
            
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        //bringing in another abstract method from parent method
        
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text)
    {
        //ensuring that the text is only numbers and decimals
        return text.matches("[0-9.]*");
    }
}
