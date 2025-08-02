//
//  BookishAdventureUITestsLaunchTests.swift
//  BookishAdventureUITests
//
//  Created by Fernando Fazio Sinigaglia on 20/03/25.
//  Copyright © 2025 Flemis. All rights reserved.
//

import XCTest

final class BookishAdventureUITestsLaunchTests: XCTestCase {

    override class var runsForEachTargetApplicationUIConfiguration: Bool {
        true
    }

    override func setUpWithError() throws {
        continueAfterFailure = false
    }

    func testLaunch() throws {
        let app = XCUIApplication()
        setupSnapshot(app)
        app.launch()

        // Insert steps here to perform after app launch but before taking a screenshot,
        // such as logging into a test account or navigating somewhere in the app

        snapshot("0Launch")
        let attachment = XCTAttachment(screenshot: app.screenshot())
        attachment.name = "Launch Screen"
        attachment.lifetime = .keepAlways
        add(attachment)
    }
}
