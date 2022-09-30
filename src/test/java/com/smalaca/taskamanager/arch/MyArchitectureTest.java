package com.smalaca.taskamanager.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.smalaca.taskamanager")
public class MyArchitectureTest {

    @ArchTest
    public static final ArchRule servicesShouldNotBeCalledFromLowLevelClasses = classes()
        .that().resideInAPackage("..service..")
        .should().onlyBeAccessed().byAnyPackage("..api.rest..", "..service..", "..processor..");

    @ArchTest
    public static final ArchRule repositoriesShouldNotBeCalledFromControllers = noClasses()
        .that().resideInAPackage("..api.rest..")
        .should().accessClassesThat().resideInAPackage("..repository..");

}
