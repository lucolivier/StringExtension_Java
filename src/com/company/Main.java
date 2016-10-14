package com.company;

/* ============================================================================

    ToDo: 

   ============================================================================ */


class T {
    static void l() { o("----------------------"); }
    static void o(Object obj) { System.out.println(obj); }
}

/* ============================================================================ */

class RangeExceptions extends Exception {
    RangeExceptions() {}
    RangeExceptions(String message) { super(message); }
}
class RE_Set_IndicesLessThanZero extends RangeExceptions {}
class RE_Set_StartIndexNotInferiorToEndIndex extends RangeExceptions {}
class RE_SetRangeFromString_VoidString extends RangeExceptions {}

class Range {
    private int startIndex = -1;
    private int endIndex = 0;

    Range() { this.startIndex = -1; this.endIndex = 0; }

    Range(int startIndex)
            throws RE_Set_StartIndexNotInferiorToEndIndex, RE_Set_IndicesLessThanZero {
        try { setStartIndex(startIndex); } catch (Exception e) { throw e; }
    }

    Range(int startIndex, int endIndex)
            throws RE_Set_StartIndexNotInferiorToEndIndex, RE_Set_IndicesLessThanZero {
        try { setIndices(startIndex, endIndex); } catch (Exception e) { throw e; }
    }

    Range(Range range) throws RE_Set_StartIndexNotInferiorToEndIndex, RE_Set_IndicesLessThanZero {
        try { setIndices(range); } catch (Exception e) { throw e; }
    }

    Range(String string) throws RE_SetRangeFromString_VoidString {
        try { setIndicesOfString(string); } catch (Exception e) { throw e; }
    }

    public String toString() {
        if (this.startIndex > -1) {
            return startIndex() + "..<" + endIndex();
        }
        return "nil";
    }

    void setStartIndex(int startIndex)
            throws RE_Set_StartIndexNotInferiorToEndIndex, RE_Set_IndicesLessThanZero {
        if (startIndex < 0) { throw new RE_Set_IndicesLessThanZero(); }
        if (startIndex >= this.endIndex) { throw new RE_Set_StartIndexNotInferiorToEndIndex(); }
        this.startIndex = startIndex;
    }
    int startIndex() { return this.startIndex; }

    void setStartIndexOrNil(int startIndex) throws RE_Set_StartIndexNotInferiorToEndIndex {
        if (startIndex > this.endIndex) { throw new RE_Set_StartIndexNotInferiorToEndIndex(); }
        this.startIndex = (startIndex < 0) ? -1 : startIndex;
    }

    void setEndIndex(int endIndex)
            throws RE_Set_StartIndexNotInferiorToEndIndex, RE_Set_IndicesLessThanZero {
        if (endIndex <= this.startIndex) { throw new RE_Set_StartIndexNotInferiorToEndIndex(); }
        if (endIndex < 0) { throw new RE_Set_IndicesLessThanZero(); }
        this.endIndex = endIndex;
    }
    int endIndex() { return this.endIndex; }

    void setIndices(int startIndex, int endIndex)
            throws RE_Set_StartIndexNotInferiorToEndIndex, RE_Set_IndicesLessThanZero {
        if (startIndex >= endIndex) { throw new RE_Set_StartIndexNotInferiorToEndIndex(); }
        try { setEndIndex(endIndex); setStartIndex(startIndex);
        } catch (Exception e) { throw e; }
    }

    void setIndices(Range range)
            throws RE_Set_StartIndexNotInferiorToEndIndex, RE_Set_IndicesLessThanZero {
        if (range == null) { setNil(); return; }
        try {
            setIndices(range.startIndex(), range.endIndex());
        } catch (Exception e) { throw e; }
    }

    void setNil() { this.startIndex = -1; }
    boolean nil() { return (this.startIndex > -1) ? false : true; }

    void setIndicesOfString(String string) throws RE_SetRangeFromString_VoidString {
        if (string.length() == 0) { throw new RE_SetRangeFromString_VoidString(); }
        this.startIndex = 0;
        this.endIndex = string.length();
    }

    boolean withinString(String string) {
        if ((!nil()) && (this.endIndex <= string.length()) ) { return true; }
        return false;
    }
}

/* ============================================================================ */

enum StringExtensionResults_t {
    Undefined                                       ("Undefined"),

    ReplaceString_Success                           ("ReplaceString_Success"),
    ReplaceString_Exception                         ("ReplaceString_Exception"),

    InsertString_Success                            ("InsertString_Success"),
    InsertString_Exception                          ("InsertString_Exception"),

    RemoveString_Success                            ("RemoveString_Success"),
    RemoveString_Exception                          ("RemoveString_Exception"),

    RemoveDoubleSpace_Success                       ("RemoveDoubleSpace_Success"),
    RemoveDoubleSpace_NoSpaceToRemove               ("RemoveDoubleSpace_NoSpaceToRemove"),
    RemoveDoubleSpace_Exception                     ("RemoveDoubleSpace_Exception"),

    RangeOfString_StringFound                       ("RangeOfString_StringFound"),
    RangeOfString_StringNotFound                    ("RangeOfString_StringNotFound"),
    RangeOfString_LastStringOccurenceFound          ("RangeOfString_LastStringOccurenceFound"),
    RangeOfString_StringOccurenceFound              ("RangeOfString_StringOccurenceFound"),
    RangeOfString_StringFoundButOccurenceNotFound   ("RangeOfString_StringFoundButOccurenceNotFound"),
    RangeOfString_Exception                         ("RangeOfString_Exception");

    private String string;
    StringExtensionResults_t(String value) { this.string = value; }
    String string() { return string; }
    public String toString() { return string; }
}

class StringExtension_Result {
    StringExtensionResults_t result = StringExtensionResults_t.Undefined;
    Exception error;
    StringExtension_Result () {}
    StringExtension_Result (StringExtensionResults_t result, Exception error) {
        this.result = result; this.error = error;
    }
    String description() { return "Result: "+result.toString(); }
    public String toString() {
        return "Result: "+result.toString();
    }
}

class StringExtension_ResultWithRange extends StringExtension_Result {
    Range range = new Range();
    String description() { return "Result: "+range.toString()+" ("+result.toString()+")"; }
    public String toString() {
        return "Result: "+range.toString()+" ("+result.toString()+")";
    }
}

/* ============================================================================ */

class StringExtensionExceptions extends Exception {
    StringExtensionExceptions() {}
    StringExtensionExceptions(String message) { super(message); }
}
class SEE_UnicodeScalar_NotAOneCharString extends StringExtensionExceptions {}
class SEE_RangeOfString_VoidString extends StringExtensionExceptions {}
class SEE_RangeOfString_BadOccurence extends StringExtensionExceptions {}
class SEE_RangeOfString_RangeNil extends StringExtensionExceptions {}
class SEE_RangeOfString_RangeOutOfString extends StringExtensionExceptions {}
class SEE_OccurenceOfString_VoidString extends StringExtensionExceptions {}
class SEE_StringWithChar_AmountToZero extends StringExtensionExceptions {}
class SEE_StringWithChar_NotAChar extends StringExtensionExceptions {}
class SEE_RemoveString_StringToRemoveVoid extends StringExtensionExceptions {}
class SEE_InsertString_StringToInsert_Void extends StringExtensionExceptions {}
class SEE_InsertString_StringAnchor_Void extends StringExtensionExceptions {}
class SEE_ReplaceString_StringToInsert_Void extends StringExtensionExceptions {}
class SEE_ReplaceString_StringAnchor_Void extends StringExtensionExceptions {}


enum StringInsertMode_t{ Before, After }

class StringExtension {
    private StringBuilder string;

    StringExtension(String string) {
        this.string = new StringBuilder(string);
    }

    String string() { return this.string.toString(); }
    public String toString() { return string(); }

    StringExtension_Result replaceString(String anchor, String string, int occurence)
            throws SEE_ReplaceString_StringToInsert_Void,
            SEE_ReplaceString_StringAnchor_Void,
            SEE_RangeOfString_VoidString,
            SEE_RangeOfString_BadOccurence,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        try { return StringExtension.replaceStringIn(this.string, anchor, string, occurence); }
        catch (Exception e) { throw e; }
    }

    static StringExtension_Result replaceStringIn(StringBuilder target, String anchor, String string, int occurence)
            throws SEE_ReplaceString_StringToInsert_Void,
            SEE_ReplaceString_StringAnchor_Void,
            SEE_RangeOfString_VoidString,
            SEE_RangeOfString_BadOccurence,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        if (string.length() == 0) { throw new SEE_ReplaceString_StringToInsert_Void(); }
        if (anchor.length() == 0) { throw new SEE_ReplaceString_StringAnchor_Void(); }

        StringExtension_ResultWithRange result;

        try {
            result  = StringExtension.rangeOfStringIn(target.toString(), anchor, occurence);
            if (result.range.nil()) {
                return new StringExtension_Result(result.result, result.error);
            }

            target.replace(result.range.startIndex(), result.range.endIndex(), string);

            return new StringExtension_Result(StringExtensionResults_t.ReplaceString_Success, null);

        } catch (Exception exception) { throw exception; }

    }

    StringExtension_Result insertToString(String string, String anchor, int occurence, StringInsertMode_t mode)
            throws SEE_InsertString_StringToInsert_Void,
            SEE_InsertString_StringAnchor_Void,
            SEE_RangeOfString_VoidString,
            SEE_RangeOfString_BadOccurence,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        try { return StringExtension.insertToStringIn(this.string, string, anchor, occurence, mode); }
        catch (Exception e) { throw e; }
    }

    static StringExtension_Result insertToStringIn(StringBuilder target, String string, String anchor, int occurence, StringInsertMode_t mode)
        throws SEE_InsertString_StringToInsert_Void,
            SEE_InsertString_StringAnchor_Void,
            SEE_RangeOfString_VoidString,
            SEE_RangeOfString_BadOccurence,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        if (string.length() == 0) { throw new SEE_InsertString_StringToInsert_Void(); }
        if (anchor.length() == 0) { throw new SEE_InsertString_StringAnchor_Void(); }

        StringExtension_ResultWithRange result;

        try {
            result  = StringExtension.rangeOfStringIn(target.toString(), anchor, occurence);
            if (result.range.nil()) {
                return new StringExtension_Result(result.result, result.error);
            }
            if (mode == StringInsertMode_t.Before) {
                target.insert(result.range.startIndex(), string);

            } else {
                target.insert(result.range.endIndex(), string);
            }
            return new StringExtension_Result(StringExtensionResults_t.InsertString_Success, null);

        } catch (Exception exception) { throw exception; }

    }

    StringExtension_Result removeString(String string, int occurence)
            throws  SEE_RemoveString_StringToRemoveVoid,
            SEE_RangeOfString_BadOccurence,
            SEE_RangeOfString_VoidString,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        try { return StringExtension.removeStringIn(this.string, string, occurence); }
        catch (Exception e) { throw e; }
    }

    static StringExtension_Result removeStringIn(StringBuilder target, String string, int occurence)
            throws  SEE_RemoveString_StringToRemoveVoid,
                    SEE_RangeOfString_BadOccurence,
                    SEE_RangeOfString_VoidString,
                    SEE_RangeOfString_RangeNil,
                    SEE_RangeOfString_RangeOutOfString,
                    RE_SetRangeFromString_VoidString,
                    RE_Set_StartIndexNotInferiorToEndIndex,
                    RE_Set_IndicesLessThanZero
    {
        if (string.length() == 0) { throw new SEE_RemoveString_StringToRemoveVoid(); }

        StringExtension_ResultWithRange resultRange;

        try {

            resultRange = StringExtension.rangeOfStringIn(target.toString(), string, occurence);
            if (resultRange.range.nil()) {
                return new StringExtension_Result(resultRange.result, resultRange.error);
            }
            target.replace(resultRange.range.startIndex(), resultRange.range.endIndex(),"");
            return new StringExtension_Result(StringExtensionResults_t.RemoveString_Success, resultRange.error);

        } catch (Exception exception) { throw exception; }
    }

    StringExtension_Result removeDoubleSpace()
            throws  SEE_RemoveString_StringToRemoveVoid,
            SEE_RangeOfString_BadOccurence,
            SEE_RangeOfString_VoidString,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        try { return StringExtension.removeDoubleSpaceIn(this.string); }
        catch (Exception e) { throw e; }
    }

    static StringExtension_Result removeDoubleSpaceIn (StringBuilder target)
        throws  SEE_RemoveString_StringToRemoveVoid,
            SEE_RangeOfString_BadOccurence,
            SEE_RangeOfString_VoidString,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        StringExtension_ResultWithRange resultRange = new StringExtension_ResultWithRange();
        StringExtension_Result result = new StringExtension_Result();
        try { resultRange.range.setIndicesOfString(target.toString()); }
        catch (Exception e) { throw e; }
        int cpt = 0;
        while (!resultRange.range.nil()) {

            try {

                resultRange.range = StringExtension.rangeOfStringIn(target.toString(),"  ",null);
                if (!resultRange.range.nil()) {

                    result = StringExtension.removeStringIn(target,"  ",1);
                    if (result.result == StringExtensionResults_t.RemoveString_Success) {
                        cpt++;
                    }
                }

            } catch (Exception exception) { throw exception; }
        }
        if (cpt == 0) {
            result.result = StringExtensionResults_t.RemoveDoubleSpace_NoSpaceToRemove;
            return result;
        } else if (result.result ==  StringExtensionResults_t.RemoveString_Success) {
            result.result = StringExtensionResults_t.RemoveDoubleSpace_Success;
            return result;
        }
        result.result = StringExtensionResults_t.RemoveString_Exception;
        return result;
    }

    StringExtension_ResultWithRange rangeOfString(String string, int occurence)
            throws SEE_RangeOfString_VoidString,
            SEE_RangeOfString_BadOccurence,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        try { return StringExtension.rangeOfStringIn(string(), string, occurence); }
        catch (Exception e) { throw e; }
    }

    static StringExtension_ResultWithRange rangeOfStringIn(String target, String string, int occurence)
        throws SEE_RangeOfString_VoidString,
            SEE_RangeOfString_BadOccurence,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        // 1=1st, -1=last, n=nth

        if (string.length() == 0) { throw new SEE_RangeOfString_VoidString(); }
        if (occurence < -1) { throw new SEE_RangeOfString_BadOccurence(); }

        StringExtension_ResultWithRange result = new StringExtension_ResultWithRange();

        if (occurence == 1) {
            result.range = StringExtension.rangeOfStringIn(target, string, null);
            result.result = (!result.range.nil())
                    ? StringExtensionResults_t.RangeOfString_StringFound
                    : StringExtensionResults_t.RangeOfString_StringNotFound;


        } else {
            Range currentRange;
            try { currentRange = new Range(target); } catch (Exception e) { throw e; }
            Range resultRange = new Range();
            Range resultRangeBkp = new Range();
            int cpt = 0;

            while (cpt != occurence) {

                try { resultRange = StringExtension.rangeOfStringIn(target, string, currentRange); }
                catch (Exception e) { throw e; }

                if (!resultRange.nil()) {
                    cpt += 1;
                    if ((resultRange.endIndex()) >= target.length()) { break; }
                    currentRange.setIndices(resultRange.endIndex(), target.length());
                } else {
                    break;
                }
                resultRangeBkp = resultRange;
            }
            if (occurence == -1) {
                if (resultRange.nil()) {
                    result.range = resultRangeBkp;
                } else {
                    result.range = resultRange;
                }
                result.result = (!result.range.nil())
                        ? StringExtensionResults_t.RangeOfString_LastStringOccurenceFound
                        : StringExtensionResults_t.RangeOfString_StringNotFound;
            } else {
                if (cpt == 0) {
                    result.range = resultRange;
                    result.result = StringExtensionResults_t.RangeOfString_StringNotFound;
                } else if (cpt == occurence) {
                    result.range = resultRange;
                    result.result = StringExtensionResults_t.RangeOfString_StringOccurenceFound;
                } else {
                    result.range.setNil();
                    result.result = StringExtensionResults_t.RangeOfString_StringFoundButOccurenceNotFound;
                }
            }

        }
        return result;
    }

    Range rangeOfString(String string, Range range)
            throws SEE_RangeOfString_VoidString,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        try { return rangeOfStringIn(string(), string, range); } catch (Exception e) { throw e; }
    }

    static Range rangeOfStringIn(String target, String string, Range range)
            throws SEE_RangeOfString_VoidString,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        if (string.length() == 0) { throw new SEE_RangeOfString_VoidString(); }

        Range currentRange = null;

        if (range != null) {
            if (range.nil()) { throw new SEE_RangeOfString_RangeNil(); }
            if (!range.withinString(target)) { throw new SEE_RangeOfString_RangeOutOfString(); }

            try { currentRange = new Range(range); } catch (Exception e) { throw e; }
        } else {
            currentRange =  new Range(target);
        }

        try {
            currentRange.setStartIndexOrNil( target.indexOf(string, currentRange.startIndex()) );
            if (!currentRange.nil()) {
                currentRange.setEndIndex(currentRange.startIndex()+string.length());
            }
            return currentRange;
        } catch (Exception e) { throw e; }
    }

    int occuranceOfString(String string)
            throws SEE_OccurenceOfString_VoidString,
            SEE_RangeOfString_VoidString,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        try { return occuranceOfStringIn(string(), string); } catch (Exception e) { throw e; }
    }

    static int occuranceOfStringIn(String target, String string)
            throws SEE_OccurenceOfString_VoidString,
            SEE_RangeOfString_VoidString,
            SEE_RangeOfString_RangeNil,
            SEE_RangeOfString_RangeOutOfString,
            RE_SetRangeFromString_VoidString,
            RE_Set_StartIndexNotInferiorToEndIndex,
            RE_Set_IndicesLessThanZero
    {
        if (string.length() == 0) { throw new SEE_OccurenceOfString_VoidString(); }
        Range resultRange = null;
        Range currentRange = null;
        try { currentRange = new Range(target); } catch (Exception e) { throw e; }
        int cpt = 0;
        while (true) {
            try { resultRange = StringExtension.rangeOfStringIn(target, string, currentRange); }
            catch (Exception e) { throw e; }
            if (!resultRange.nil()) {
                cpt += 1;
                if ((resultRange.endIndex()) >= target.length()) { break; }
                currentRange.setIndices(resultRange.endIndex(), target.length());
            } else {
                break;
            }
        }
        return cpt;
    }

    static String stringWithChar(String character, int amount)
            throws SEE_StringWithChar_AmountToZero, SEE_StringWithChar_NotAChar
    {
        if (character.length() != 1) { throw new SEE_StringWithChar_NotAChar(); }
        if (amount < 1) { throw new SEE_StringWithChar_AmountToZero(); }
        StringBuilder res = new StringBuilder("");
        for (int i=0; i<amount; i++) {
            res.append(character);
        }
        return res.toString();
    }

    int unicodeScalar() { return StringExtension.unicodeScalar(this.string.toString()); }

    static int unicodeScalar(String string) {
        T.o(string.length());
        return string.codePointAt(0);
    }
}


public class Main {

    public static void main(String[] args) {

        T.o("# unicodeScalar");

        try {
            T.o(StringExtension.unicodeScalar("Ç"));

            StringExtension SE01 = new StringExtension("Ç");
            T.o(SE01.unicodeScalar());

            T.o("\uD83D\uDE07".codePointAt(0));
            T.o(StringExtension.unicodeScalar("\uD83D\uDE0E"));

        }
        catch (Exception e) { T.o(e); }

        T.l();

        T.o("# Range");

        //String str = "ÇHello, World, great World!";
        StringExtension text = new StringExtension("The student hat is a very nice course!");

        Range textRange = null;
        try { textRange = new Range(text.string()); } catch (Exception e) { T.o(e); }
        T.o(textRange);

        T.o("# rangeOfStringIn & rangeOfString");

        StringExtension text2 = new StringExtension("A Apple");
        try {
            Range range = StringExtension.rangeOfStringIn(text.string(), "hat", null);
            T.o(range);
            range = StringExtension.rangeOfStringIn(text.string(), "Apple", null);
            T.o(range);
            range = StringExtension.rangeOfStringIn(text2.string(), "e", null);
            T.o(range);
            Range Scope = new Range(1,38);
            range = StringExtension.rangeOfStringIn(text.string(), "The", Scope);
            T.o(range);
            Scope.setIndices(37,38);
            T.o(Scope);
            range = StringExtension.rangeOfStringIn(text.string(), "The", Scope);
            T.o(range);

            range = StringExtension.rangeOfStringIn(text.string(), "very", new Range(text.string()));
            T.o(range);
            range = text.rangeOfString("very", new Range(text.string()));
            T.o(range);

            T.l();
            T.o("# occuranceOfStringIn & occuranceOfString");

            String text3 = "Here a new text, to test new function, with new code!";
            int occ = StringExtension.occuranceOfStringIn(text3, "new");
            T.o(occ);
            occ = StringExtension.occuranceOfStringIn(text3, "e");
            T.o(occ);
            StringExtension text4 = new StringExtension("I'm a scn, because scn is magic, and treat about spirit. scn is nice!");
            T.o(text4.occuranceOfString("s-cn"));

            T.l();
            T.o("# stringWithChar");

            T.o(StringExtension.stringWithChar("%", 20));
        } catch (Exception e) { T.o(e); }

        T.l();
        T.o("# result class");

        StringExtension_Result res = new StringExtension_Result();
        T.o(res.result.string());

        T.l();
        T.o("# rangeOfStringIn & rangeOfString with occurence");

        try {
            String text5 = "The way to get you out the down is to do your diary work! You know that and you experienced it already many times";
            StringExtension_ResultWithRange resr = StringExtension.rangeOfStringIn(text5, "to", 3);
            T.o(resr);
            StringExtension setext5 = new StringExtension(text5);
            resr = setext5.rangeOfString("out", 1);
            T.o(resr);

            StringExtension settext6 = new StringExtension("to-to111");
            resr = settext6.rangeOfString("to", -1);
            T.o(resr);

        } catch (Exception e) { T.o(e); }

        T.l();
        T.o("# removeDoubleSpaceIn & removeDoubleSpace");

        String str1 = "Here's a     new string  work on it! new?    I'm not sure what I mean! new";
        StringBuilder strb1 = new StringBuilder(str1);

        StringExtension strb2 = new StringExtension("  -     -  ");

        StringExtension_Result resrem;
        try {

            resrem = StringExtension.removeStringIn(strb1, "new", -1);
            T.o(resrem.description());
            T.o(">"+strb1.toString()+"<");

            resrem = StringExtension.removeDoubleSpaceIn(strb1);
            T.o(resrem.description());
            T.o(">"+strb1.toString()+"<");

            T.o(">>"+strb2.toString()+"<<");
            resrem = strb2.removeDoubleSpace();
            T.o(resrem.description());
            T.o(">"+strb2.toString()+"<");

        } catch (Exception e) { T.o(e); }

        T.l();
        T.o("# insertToStringIn & insertToString");

        StringBuilder seb_01 = new StringBuilder("Hello, World!!");
        StringExtension se_01 = new StringExtension(seb_01.toString());
        StringExtension_Result seres;
        try {
            seres = StringExtension.insertToStringIn(seb_01,"?-?-?","!",-1,StringInsertMode_t.Before);
            T.o(seres.description());
            T.o(">"+seb_01.toString()+"<");

            T.o(">"+se_01.toString()+"<");
            seres = se_01.insertToString("#-#-#","!",3,StringInsertMode_t.After);
            T.o(seres.description());
            T.o(">"+se_01.toString()+"<");

        } catch (Exception e) { T.o(e); }


        T.l();
        T.o("# replaceStringIn & replaceString");

        StringBuilder seb_02 = new StringBuilder("Hello, World!!");
        T.o(seb_02);
        StringExtension se_02 = new StringExtension(seb_02.toString());
        StringExtension_Result seres02;
        try {
            seres02 = StringExtension.replaceStringIn(seb_02,"!","?-?-?",-1);
            T.o(seres02.description());
            T.o(">"+seb_02.toString()+"<");

            T.o(">"+seb_02.toString()+"<");
            seres02 = se_02.replaceString("!","#-#-#",3);
            T.o(seres02.description());
            T.o(">"+se_02.toString()+"<");

        } catch (Exception e) { T.o(e); }

    }
}

