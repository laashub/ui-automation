/*
 * Copyright 2016-17 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mmarquee.automation.controls;

import mmarquee.automation.AutomationException;
import mmarquee.automation.pattern.PatternNotFoundException;
import mmarquee.automation.pattern.Text;
import mmarquee.automation.pattern.Value;

/**
 * Wrapper around the edit box element.
 * @author Mark Humphreys
 * Date 26/01/2016.
 */
public class AutomationEditBox
        extends AutomationBase
        implements Valueable {

    /**
     * The value pattern.
     */
    private Value valuePattern;

    /**
     * The text pattern.
     */
    private Text textPattern;

    /**
     * Gets the value of the control.
     * @return The string value of the control.
     * @throws AutomationException Something has gone wrong.
     * @throws PatternNotFoundException Expected pattern not found.
     */
    public String getValue() throws AutomationException, PatternNotFoundException {
        if (this.valuePattern == null) {
            this.valuePattern = this.getValuePattern();
        }

        return valuePattern.value();
    }

    /**
     * Gets the text of the control.
     * @return The string value of the control.
     * @throws AutomationException Something has gone wrong.
     * @throws PatternNotFoundException Expected pattern not found.
     */
    public String getText() throws AutomationException, PatternNotFoundException {
        if (this.textPattern == null) {
            this.textPattern = this.getTextPattern();
        }

        return textPattern.getText();
    }

    /**
     * Sets the value of the edit box.
     * @param value The value to set.
     * @throws AutomationException Something has gone wrong.
     * @throws PatternNotFoundException Expected pattern not found.
     */
    public void setValue(String value) throws AutomationException, PatternNotFoundException {
        if (this.valuePattern == null) {
            this.valuePattern = this.getValuePattern();
        }

        this.valuePattern.setValue(value);
    }

    /**
     * Whether the element is read only.
     * @return True if readonly, otherwise false.
     * @throws AutomationException Something has gone wrong.
     * @throws PatternNotFoundException Expected pattern not found.
     */
    public boolean isReadOnly()throws AutomationException, PatternNotFoundException {
        if (this.valuePattern == null) {
            this.valuePattern = this.getValuePattern();
        }

        return this.valuePattern.isReadOnly();
    }

    /**
     * Whether the element is a password.
     * @return True if it's a password, otherwise false.
     * @throws AutomationException Automation error.
     */
    public boolean isPassword() throws AutomationException {
        return this.element.isPassword();
    }

    /**
     * Constructor for the AutomationEditBox.
     * @param builder The builder.
     */
    public AutomationEditBox(final ElementBuilder builder) {
        super(builder);
        this.valuePattern = builder.getValue();
    }
}
