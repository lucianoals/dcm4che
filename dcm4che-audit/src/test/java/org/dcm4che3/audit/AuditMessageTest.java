/* ***** BEGIN LICENSE BLOCK *****
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
 * Agfa Healthcare.
 * Portions created by the Initial Developer are Copyright (C) 2012
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
 * ***** END LICENSE BLOCK ***** */

package org.dcm4che3.audit;


import java.io.StringReader;
import java.util.Calendar;
import java.util.HashSet;
import org.junit.Test;

/**
 * @author Gunter Zeilinger <gunterze@gmail.com>
 * @author Vrinda Nayak <vrinda.nayak@j4care.com>
 *
 */
public class AuditMessageTest {

    private static final String AUDIT_MESSAGE =
            "<AuditMessage>"
            + "<EventIdentification"
            + " EventActionCode=\"C\""
            + " EventDateTime=\"2014-03-12T14:10:50.934+01:00\""
            + " EventOutcomeIndicator=\"0\">"
            + "<EventID"
            + " code=\"110104\""
            + " displayName=\"DICOM Instances Transferred\""
            + " codeSystemName=\"DCM\"/>"
            + "</EventIdentification>"
            + "<ActiveParticipant"
            + " UserID=\"123\""
            + " AlternativeUserID=\"AETITLES=AEFOO\""
            + " UserIsRequestor=\"false\""
            + " NetworkAccessPointID=\"192.168.1.2\""
            + " NetworkAccessPointTypeCode=\"2\">"
            + "<RoleIDCode"
            + " code=\"110153\""
            + " displayName=\"Source\""
            + " codeSystemName=\"DCM\"/>"
            + "</ActiveParticipant>"
            + "<ActiveParticipant"
            + " UserID=\"67562\""
            + " AlternativeUserID=\"AETITLES=AEPACS\""
            + " UserIsRequestor=\"false\""
            + " NetworkAccessPointID=\"192.168.1.5\""
            + " NetworkAccessPointTypeCode=\"2\">"
            + "<RoleIDCode"
            + " code=\"110152\""
            + " displayName=\"Destination\""
            + " codeSystemName=\"DCM\"/>"
            + "</ActiveParticipant>"
            + "<ActiveParticipant"
            + " UserID=\"smitty@readingroom.hospital.org\""
            + " AlternativeUserID=\"smith@nema\""
            + " UserName=\"Dr. Smith\""
            + " UserIsRequestor=\"true\""
            + " NetworkAccessPointID=\"192.168.1.2\""
            + " NetworkAccessPointTypeCode=\"2\">"
            + "<RoleIDCode"
            + " code=\"110153\""
            + " displayName=\"Source\""
            + " codeSystemName=\"DCM\"/>"
            + "</ActiveParticipant>"
            + "<AuditSourceIdentification"
            + " AuditEnterpriseSiteID=\"Hospital\""
            + " AuditSourceID=\"ReadingRoom\">"
            + "<AuditSourceTypeCode code=\"1\"/>"
            + "</AuditSourceIdentification>"
            + "<ParticipantObjectIdentification"
            + " ParticipantObjectID=\"1.2.840.10008.2.3.4.5.6.7.78.8\""
            + " ParticipantObjectTypeCode=\"2\""
            + " ParticipantObjectTypeCodeRole=\"4\""
            + " ParticipantObjectDataLifeCycle=\"1\">"
            + "<ParticipantObjectIDTypeCode"
            + " code=\"110180\""
            + " displayName=\"Study Instance UID\""
            + " codeSystemName=\"DCM\"/>"
            + "<ParticipantObjectDescription>"
            + "<MPPS UID=\"1.2.840.10008.1.2.3.4.5\"/>"
            + "<Accession Number=\"12341234\"/>"
            + "<SOPClass UID=\"1.2.840.10008.5.1.4.1.1.2\" NumberOfInstances=\"1500\"/>"
            + "<SOPClass UID=\"1.2.840.10008.5.1.4.1.1.11.1\" NumberOfInstances=\"3\"/>"
            + "</ParticipantObjectDescription>"
            + "</ParticipantObjectIdentification>"
            + "<ParticipantObjectIdentification"
            + " ParticipantObjectID=\"ptid12345\""
            + " ParticipantObjectTypeCode=\"1\""
            + " ParticipantObjectTypeCodeRole=\"1\">"
            + "<ParticipantObjectIDTypeCode code=\"2\"/>"
            + "<ParticipantObjectName>John Doe</ParticipantObjectName>"
            + "</ParticipantObjectIdentification>"
            + "</AuditMessage>";

    @Test
    public void testDICOMInstancesTransferred() throws Exception {
        BuildEventIdentification ei = new BuildEventIdentification.Builder(AuditMessages.EventID.DICOMInstancesTransferred,
                AuditMessages.EventActionCode.Create, Calendar.getInstance(), AuditMessages.EventOutcomeIndicator.Success).build();
        BuildActiveParticipant[] activeParticipants = new BuildActiveParticipant[3];
        activeParticipants[0] = new BuildActiveParticipant.Builder("123", "192.168.1.2").requester(false)
                .altUserID(AuditMessages.alternativeUserIDForAETitle("AEFOO")).roleIDCode(AuditMessages.RoleIDCode.Source).build();
        activeParticipants[1] = new BuildActiveParticipant.Builder("67562", "192.168.1.5").requester(false)
                .altUserID(AuditMessages.alternativeUserIDForAETitle("AEPACS")).roleIDCode(AuditMessages.RoleIDCode.Destination).build();
        activeParticipants[2] = new BuildActiveParticipant.Builder("smitty@readingroom.hospital.org", "192.168.1.2")
                .requester(true).altUserID("smith@nema").userName("Dr. Smith").roleIDCode(AuditMessages.RoleIDCode.Source).build();

        ParticipantObjectContainsStudy pocs = AuditMessages.getPocs("1.2.840.10008.2.3.4.5.6.7.78.8");
        HashSet<SOPClass> sopClasses = new HashSet<>();
        sopClasses.add(AuditMessages.getSOPC(null,  "1.2.840.10008.5.1.4.1.1.2", 1500));
        sopClasses.add(AuditMessages.getSOPC(null, "1.2.840.10008.5.1.4.1.1.11.1", 3));
        BuildParticipantObjectDescription pod = new BuildParticipantObjectDescription.Builder(sopClasses, pocs)
                .acc(AuditMessages.getAccessions("12341234")).mpps(AuditMessages.getMPPS("1.2.840.10008.1.2.3.4.5")).build();
        BuildParticipantObjectIdentification poiStudy = new BuildParticipantObjectIdentification.Builder("1.2.840.10008.2.3.4.5.6.7.78.8",
                AuditMessages.ParticipantObjectIDTypeCode.StudyInstanceUID, AuditMessages.ParticipantObjectTypeCode.SystemObject,
                AuditMessages.ParticipantObjectTypeCodeRole.Resource).desc(AuditMessages.getPODesc(pod))
                .lifeCycle(AuditMessages.ParticipantObjectDataLifeCycle.OriginationCreation).build();
        BuildParticipantObjectIdentification poiPatient = new BuildParticipantObjectIdentification.Builder("ptid12345",
                AuditMessages.ParticipantObjectIDTypeCode.PatientNumber, AuditMessages.ParticipantObjectTypeCode.Person,
                AuditMessages.ParticipantObjectTypeCodeRole.Patient).name("John Doe").desc(AuditMessages.getPODesc(pod)).build();

        AuditMessage msg = AuditMessages.createMessage(ei, activeParticipants, poiStudy, poiPatient);
        msg.getAuditSourceIdentification().add(AuditMessages.createAuditSourceIdentification("Hospital", "ReadingRoom",
                        AuditMessages.AuditSourceTypeCode.EndUserDisplayDevice));
        AuditMessages.toXML(msg, System.out, true);
    }

    @Test
    public void testFromXML() throws Exception {
       AuditMessage msg = AuditMessages.fromXML(new StringReader(AUDIT_MESSAGE));
    }

}
