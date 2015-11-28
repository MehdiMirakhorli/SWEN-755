class CreateReminders < ActiveRecord::Migration
  def change
    create_table :reminders do |t|
      t.belongs_to :user, index: true
      t.string :name
      t.text :content

      t.timestamps
    end
  end
end
