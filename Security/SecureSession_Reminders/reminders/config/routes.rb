Rails.application.routes.draw do
  devise_for :users
  resources :reminders
  root 'reminders#index'

end
