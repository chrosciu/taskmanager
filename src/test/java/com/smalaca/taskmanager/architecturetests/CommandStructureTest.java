package com.smalaca.taskmanager.architecturetests;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

class CommandStructureTest {
    @Test
    void commandsShouldDependOnlyOnCommand() {
        JavaClasses javaClasses = ProjectManagementClasses.getAll();

        ArchRuleDefinition.classes()
                .that().resideInAPackage("..command..")
                .should().onlyAccessClassesThat().resideInAnyPackage("..command..", "java..")

                .check(javaClasses);
    }
}
