/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008-2013 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU
 * General Public License Version 3 only ("GPL").  
 * You may not use this file except in compliance with the License. 
 * You can obtain a copy of the License at http://www.gnu.org/licenses/gpl-3.0.html
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 * 
 */

package org.jopendocument.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * A panel listing a set of tasks and their progress bars.
 */
public class WaitIndeterminatePanel extends JPanel {

    public WaitIndeterminatePanel(Object o) {
        this(Collections.singletonList(o));
    }

    public WaitIndeterminatePanel(List<?> taskList) {
        super(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(2, 2, 1, 2);
        c.weighty = 1;

        for (int i = 0; i < taskList.size(); i++) {
            c.gridx = 0;
            c.weightx = 0;
            final JLabel label = new JLabel(taskList.get(i).toString());
            label.setHorizontalTextPosition(SwingConstants.LEADING);
            this.add(label, c);
            c.weightx = 1;
            c.gridx++;
            final JProgressBar pb = new JProgressBar();
            pb.setIndeterminate(true);
            this.add(pb, c);
            c.gridy++;
        }
    }

    public void taskEnded(int idTask) {
        final JLabel label = (JLabel) this.getComponent(idTask * 2);
        // TODO create checkMark.png
        label.setIcon(new ImageIcon(WaitIndeterminatePanel.class.getResource("tache.png")));
        final Component pb = this.getComponent(idTask * 2 + 1);
        pb.setVisible(false);
        this.revalidate();
    }
}
