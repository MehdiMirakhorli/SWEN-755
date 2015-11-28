json.array!(@reminders) do |reminder|
  json.extract! reminder, :id, :name, :content
  json.url reminder_url(reminder, format: :json)
end
