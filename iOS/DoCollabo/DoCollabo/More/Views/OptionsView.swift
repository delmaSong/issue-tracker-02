//
//  OptionsView.swift
//  DoCollabo
//
//  Created by Cory Kim on 2020/06/23.
//  Copyright © 2020 delma. All rights reserved.
//

import UIKit

final class OptionsView: UIView {

    private var optionsStackView: UIStackView!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        configure()
    }
    
    func addOptions(buttons: [UIButton]) {
        for button in buttons {
            optionsStackView.addArrangedSubview(button)
        }
    }
}

// MARK:- Configuration

extension OptionsView {
    private func configure() {
        configureUI()
        configureLayout()
    }
    
    private func configureUI() {
        backgroundColor = .tertiarySystemBackground
        roundCorner(cornerRadius: 16.0)
        optionsStackView = UIStackView()
        optionsStackView.axis = .vertical
        optionsStackView.distribution = .fillEqually
        optionsStackView.spacing = 0
    }
    
    private func configureLayout() {
        addSubview(optionsStackView)
        optionsStackView.fillSuperview()
    }
}
