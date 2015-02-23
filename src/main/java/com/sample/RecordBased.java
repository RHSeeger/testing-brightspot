package com.sample;

import com.psddev.cms.db.ToolUi;
import com.psddev.dari.db.Record;
import com.psddev.dari.util.ObjectUtils;

/**
 * Created by rhseeger on 2/23/15.
 * A sample class to show testing of something that requires the database
 */
public class RecordBased extends Record {
    @ToolUi.Note("The title as specified by the original source")
    private String sourceTitle;

    @ToolUi.Note("The title as specified by the editors")
    private String editorialTitle;

    @Indexed
    @ToolUi.Note("The title to display on the front end")
    private String displayTitle;

    /** ACCESSORS **/
    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public String getEditorialTitle() {
        return editorialTitle;
    }

    public void setEditorialTitle(String editorialTitle) {
        this.editorialTitle = editorialTitle;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    /** UTILITY **/
    protected String generateDisplayTitle() {
        return (ObjectUtils.isBlank(editorialTitle) ? sourceTitle : editorialTitle);
    }

    /** Overrides **/
    @Override
    public void beforeSave() {
        displayTitle = generateDisplayTitle();
    }

}
