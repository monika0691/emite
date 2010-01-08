package com.calclab.emite.xep.dataforms.client;

import java.util.ArrayList;
import java.util.List;

import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.packet.MatcherFactory;

/**
 * 
 * XEP-0004 Form
 * 
 */
public class Form {

    public enum Type {
        /**
         * The form-processing entity is asking the form-submitting entity to
         * complete a form.
         */
        form,
        /**
         * The form-submitting entity is submitting data to the form-processing
         * entity. The submission MAY include fields that were not provided in
         * the empty form, but the form-processing entity MUST ignore any fields
         * that it does not understand.
         */
        submit,
        /**
         * The form-submitting entity has cancelled submission of data to the
         * form-processing entity.
         */
        cancel,
        /**
         * The form-processing entity is returning data (e.g., search results)
         * to the form-submitting entity, or the data is a generic data set.
         */
        result
    }

    private static final String DATA_XMLS = "jabber:x:data";

    public static Form parse(final IPacket packet) {
        final Form form = new Form();
        final IPacket x = packet.getFirstChildInDeep(MatcherFactory.byNameAndXMLNS("x", DATA_XMLS));
        form.setTitle(x.getFirstChild("title").getText());
        form.setType(Type.valueOf(x.getAttribute("type")));
        final List<String> instructions = new ArrayList<String>();
        for (final IPacket instruction : x.getChildren(MatcherFactory.byName("instructions"))) {
            instructions.add(instruction.getText());
        }
        form.setInstructions(instructions);
        form.setFields(Field.parseList(x));
        form.setReported(Reported.parse(x.getFirstChild(MatcherFactory.byName("reported"))));
        form.setItems(Item.parse(x.getChildren(MatcherFactory.byName("item"))));
        return form;
    }

    private Type type;
    private String title;
    private List<String> instructions;
    private List<Field> fields;

    /**
     *In some contexts (e.g., the results of a search request), it may be
     * necessary to communicate multiple items. Therefore, a data form of type
     * "result" MAY contain two child elements not described in the basic syntax
     * above: 1. One and only <reported/> element, which can be understood as a
     * "table header" describing the data to follow. 2. Zero or more <item/>
     * elements, which can be understood as "table cells" containing data (if
     * any) that matches the request.
     */
    private Reported reported;
    private List<Item> items;

    public Form() {

    }

    public List<Field> getFields() {
        return fields;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public List<Item> getItems() {
        return items;
    }

    public Reported getReported() {
        return reported;
    }

    public String getTitle() {
        return title;
    }

    public Type getType() {
        return type;
    }

    public void setFields(final List<Field> fields) {
        this.fields = fields;
    }

    public void setInstructions(final List<String> instructions) {
        this.instructions = instructions;
    }

    public void setItems(final List<Item> items) {
        this.items = items;
    }

    public void setReported(final Reported reported) {
        this.reported = reported;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setType(final Type type) {
        this.type = type;
    }
}
