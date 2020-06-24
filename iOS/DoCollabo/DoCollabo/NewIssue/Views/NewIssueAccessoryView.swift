//
//  NewIssueAccessoryView.swift
//  DoCollabo
//
//  Created by delma on 2020/06/22.
//  Copyright © 2020 delma. All rights reserved.
//

import UIKit

protocol NewIssueAccessoryDelegate: class {
    func selectAssigneeButtonDidTap()
    func selectLabelsButtonDidTap()
    func selectMilestoneButtonDidTap()
}

final class NewIssueAccessoryView: UIView {
    
    @IBOutlet weak var frameView: UIView!
    
    weak var delegate: NewIssueAccessoryDelegate?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        configure()
    }
    
    private func configure() {
        Bundle.main.loadNibNamed(String(describing: Self.self), owner: self, options: nil)
        addSubview(frameView)
        frameView.frame = self.bounds
        configureUI()
    }
    
    private func configureUI() {
        frameView.roundCorner(cornerRadius: 12.0)
        frameView.drawShadow(color: .black, offset: CGSize(width: 1, height: 1), radius: 4, opacity: 0.3)
    }
    
    @IBAction func selectAssignees(_ sender: UIButton) {
        delegate?.selectAssigneeButtonDidTap()
    }
    
    @IBAction func selectLabels(_ sender: UIButton) {
        delegate?.selectLabelsButtonDidTap()
    }
    
    @IBAction func selectMilestones(_ sender: UIButton) {
        delegate?.selectMilestoneButtonDidTap()
    }
}
