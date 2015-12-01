class RemindersController < ApplicationController
  before_action :set_reminder, only: [:show, :edit, :update, :destroy]
  before_filter :check_reminder_exists, only: [:show, :edit, :update, :destroy]
  before_action :authenticate_user!

  respond_to :html

  def index
    @reminders = Reminder.where(user_id: current_user.id)
    respond_with(@reminders)
  end

  def show
    if !user_authorized?
      flash.now[:alert] = "You are unauthorized to view this reminder."
      render "error"
      return
    end

    respond_with(@reminder)
  end

  def new
    @reminder = Reminder.new
    respond_with(@reminder)
  end

  def edit
    if !user_authorized?
      flash.now[:alert] = "You are unauthorized to edit this reminder."
      render "error"
      return
    end

    respond_with(@reminder)
  end

  def create
    @reminder = Reminder.new(reminder_params)
    @reminder.user_id = current_user.id
    @reminder.save
    redirect_to :action => :index
  end

  def update
    if !user_authorized?
      flash.now[:alert] = "You are unauthorized to update this reminder."
      render "error"
      return
    end

    @reminder.update(reminder_params)
    redirect_to :action => :index
  end

  def destroy
    if !user_authorized?
      flash.now[:alert] = "You are unauthorized to delete this reminder."
      render "error"
      return
    end


    @reminder.destroy
    respond_with(@reminder)
  end

  private
    def set_reminder
      @reminder = Reminder.find_by(id: params[:id])
    end

    def reminder_params
      params.require(:reminder).permit(:name, :content)
    end

    def user_authorized?
      if @reminder.user_id == current_user.id
        return true
      end
      return false
    end

    def check_reminder_exists
      if @reminder.nil?
      flash.now[:alert] = "Reminder does not exist!"
      render "error"
      return
    end
  end
        # render json: {nil: @reminder.nil?, params: params}
end
