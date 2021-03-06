/*
 * *** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), hosted at https://github.com/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * J4Care.
 * Portions created by the Initial Developer are Copyright (C) 2013
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * See @authors listed below
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * *** END LICENSE BLOCK *****
 */

package org.dcm4che3.audit;

/**
 * @author Vrinda Nayak <vrinda.nayak@j4care.com>
 * @since June 2016
 */

public class BuildParticipantObjectIdentification {
    public final String id;
    public final AuditMessages.ParticipantObjectIDTypeCode idType;
    public final String name;
    public final byte[] query;
    public final String type;
    public final String role;
    public final String lifeCycle;
    public final String sensitivity;
    public final ParticipantObjectDescription desc;
    public final ParticipantObjectDetail[] detail;

    public static class Builder {
        private final String id;
        private final AuditMessages.ParticipantObjectIDTypeCode idType;
        private String name;
        private byte[] query;
        private String type;
        private String role;
        private String lifeCycle;
        private String sensitivity;
        private ParticipantObjectDescription desc;
        private ParticipantObjectDetail[] detail = {};

        public Builder(String id, AuditMessages.ParticipantObjectIDTypeCode idType,
                       String type, String role) {
            this.id = id;
            this.idType = idType;
            this.type = type;
            this.role = role;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder query(byte[] val) {
            query = val;
            return this;
        }

        public Builder lifeCycle(String val) {
            lifeCycle = val;
            return this;
        }

        public Builder sensitivity(String val) {
            sensitivity = val;
            return this;
        }

        public Builder desc(ParticipantObjectDescription val) {
            desc = val;
            return this;
        }

        public Builder detail(ParticipantObjectDetail... val) {
            detail = val;
            return this;
        }

        public BuildParticipantObjectIdentification build() {
            return new BuildParticipantObjectIdentification(this);
        }
    }

    private BuildParticipantObjectIdentification(Builder builder) {
        id = builder.id;
        idType = builder.idType;
        name = builder.name;
        query = builder.query;
        type = builder.type;
        role = builder.role;
        lifeCycle = builder.lifeCycle;
        sensitivity = builder.sensitivity;
        desc = builder.desc;
        detail = builder.detail;
    }
}
